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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class PerfilAjeno : AppCompatActivity(){
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_ajeno)

        val bundle = intent.extras

        //Tomamos el nombre de usuario del registro
        val nombre = bundle?.getString("nombre") ?: ""
        val descripcion = bundle?.getString("descripcion")?: ""
        val edad= bundle?.getString("edad")?: ""
        val intereses = bundle?.getStringArrayList("intereses")?: ArrayList()
        val fotoPerfil = bundle?.getString("fotoPerfil")?: ""
        title=nombre

        val descripcionVista = findViewById<TextView>(R.id.descripcionajena)
        val fotoPerfilVista = findViewById<ImageView>(R.id.imagenajena)
        val edadVista=findViewById<TextView>(R.id.edadajena)
        val localfile = File.createTempFile("tempImage", "jpg")
        val interesesPerfil = findViewById<TextView>(R.id.interesesajenos)
        FirebaseStorage.getInstance().getReferenceFromUrl(fotoPerfil).getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            fotoPerfilVista.setImageBitmap(bitmap)
        }
        edadVista.text =edad
        descripcionVista.text =descripcion


        val haciaChat = findViewById<Button>(R.id.botonchat)
        haciaChat.setOnClickListener{

            val intent = Intent(this,ListOfChats::class.java)
            intent.putExtra("usuario", auth.currentUser?.uid)
            startActivity(intent)


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

   /* private fun newChat(){
        //Generamos el uid del chat de manera aleatoria para identificarlo
        val chatId = UUID.randomUUID().toString()

        //Además debemos tener en cuenta el otro usuario al que va dirigido el chat
        val usuarioReceptor = textoChat.text.toString()

        //Creamos una lista con los usuarios que intervienen en el chat
        val usuariosChat = listOf(usuario,usuarioReceptor)

        //Creamos el chat en cuestión
        val chat = Chat(
            id = chatId,
            nombre = "$usuarioReceptor",
            usuarios = usuariosChat
        )

        //Ahora debemos incluir este chat en la database del usuario que lo inicia y del que lo recibe
        db.collection("chats").document(chatId).set(chat)
        db.collection("Usuarios").document(usuario).collection("chats").document(chatId).set(chat)
        db.collection("Usuarios").document(usuarioReceptor).collection("chats").document(chatId).set(chat)

        //Enviamos a la siguiente pantalla el usuario y el chatId
        val intent = Intent(this,ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }*/
}