package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class PerfilAjeno : AppCompatActivity(){
    private val auth = Firebase.auth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_ajeno)

        val bundle = intent.extras

        //Tomamos el nombre de usuario del registro
        val nombre = bundle?.getString("nombre") ?: ""
        val descripcion = bundle?.getString("descripcion")?: ""
        val edad= bundle?.getString("edad")?: ""
        val genero= bundle?.getString("genero")?: ""
        val intereses = bundle?.getStringArrayList("intereses")?: ArrayList()
        val fotoPerfil = bundle?.getString("fotoPerfil")?: ""
        val uidAjeno = bundle?.getString("uid")?: ""
        val uidPropio = auth.currentUser?.uid ?:""
        title=nombre

        val descripcionVista = findViewById<TextView>(R.id.descripcionajena)
        val fotoPerfilVista = findViewById<ImageView>(R.id.imagenajena)
        val edadVista=findViewById<TextView>(R.id.edadajena)
        val localfile = File.createTempFile("tempImage", "jpg")
        val interesesPerfil = findViewById<TextView>(R.id.interesesajenos)
        val generoVista=findViewById<TextView>(R.id.generoajeno)
        FirebaseStorage.getInstance().getReferenceFromUrl(fotoPerfil).getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            fotoPerfilVista.setImageBitmap(bitmap)
        }
        edadVista.text =edad
        descripcionVista.text =descripcion
        generoVista.text=genero

        val refUsuarios = db.collection("Usuarios").document(uidPropio)

        val haciaChat = findViewById<Button>(R.id.botonchat)
        haciaChat.setOnClickListener{
            refUsuarios.collection("chats").get().addOnSuccessListener { chats ->
                var existeChat = false
                for (chat in chats){
                    val usuarioEnChat : ArrayList<String> = chat.get("usuarios") as ArrayList<String>
                    if(usuarioEnChat.contains(uidAjeno) && usuarioEnChat.contains(uidPropio) ){
                        val intent = Intent(this, ChatActivity::class.java)
                        intent.putExtra("chatId",chat.id)
                        intent.putExtra("usuario",uidPropio)
                        startActivity(intent)
                        existeChat=true
                    }
                }
                if(!existeChat){
                    newChat(uidAjeno, uidPropio)
                }

            }



        }



        //Al iniciar la app, estableceremos que los botones de la barra de menu principal tengan
        //un onclick preestablecido

        val chatButton = findViewById<ImageView>(R.id.imgChats)
        chatButton.setOnClickListener{

            val intent = Intent(this,ListOfChats::class.java)
            intent.putExtra("usuario", auth.currentUser?.uid)
            startActivity(intent)


        }

        val initButton = findViewById<ImageView>(R.id.imgInicio)
        initButton.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        val perfilButton = findViewById<ImageView>(R.id.imgPerfil)
        perfilButton.setOnClickListener{
            val intent = Intent(this, Perfil_propio::class.java)
            startActivity(intent)
        }


        var interesesEnTexto: String = ""
        for(interes in intereses){
            if (intereses.indexOf(interes) == (intereses.size -1)){
                interesesEnTexto += interes
            }
            else{
                interesesEnTexto += interes+ ", "
            }
        }
        interesesPerfil.text = interesesEnTexto




    }

    private fun newChat(uidAjeno : String, uidPropio : String){
        //Generamos el uid del chat de manera aleatoria para identificarlo
        val chatId = UUID.randomUUID().toString()


        //Creamos una lista con los usuarios que intervienen en el chat
        val usuariosChat = listOf(uidPropio,uidAjeno)

        //Creamos el chat en cuestión
        val chat = Chat(
            id = chatId,
            nombre = uidAjeno,
            usuarios = usuariosChat
        )

        //Ahora debemos incluir este chat en la database del usuario que lo inicia y del que lo recibe
        db.collection("chats").document(chatId).set(chat)
        db.collection("Usuarios").document(uidPropio).collection("chats").document(chatId).set(chat)
        db.collection("Usuarios").document(uidAjeno).collection("chats").document(chatId).set(chat)

        //Enviamos a la siguiente pantalla el usuario y el chatId
        val intent = Intent(this,ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("usuario",uidPropio)
        intent.putExtra("usuarioReceptor",uidAjeno)
        startActivity(intent)
    }
}