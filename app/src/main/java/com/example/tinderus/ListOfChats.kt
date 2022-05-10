package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.*

class ListOfChats : AppCompatActivity() {

    /////////////////////
    //    Variables    //
    /////////////////////

    private var usuario = ""
    private var usuarioreceptor = ""
    private lateinit var listChatsRecyclerView:RecyclerView
    private var db = Firebase.firestore
    private val auth = Firebase.auth
    /////////////////////
    //    Funciones    //
    /////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_of_chats)

        //Al iniciar la app, estableceremos que los botones de la barra de menu principal tengan
        //un onclick preestablecido
        title="Chats"
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

        //Actualizamos las variables
        listChatsRecyclerView = findViewById<RecyclerView>(R.id.listChatsRecyclerView)

        //Obtenemos el nombre de usuario de la vista anterior

        val bundle = intent.extras
        usuario = bundle?.getString("usuario") ?: ""


        //Comprobamos si este valor es nulo. En caso negativo ejecutamos
        if(usuario != ""){
            //Toast.makeText(this, "Antes de iniciar la vista", Toast.LENGTH_SHORT).show()
            initViews()
        }
    }

    private fun initViews(){

        listChatsRecyclerView.layoutManager = LinearLayoutManager(this)
        listChatsRecyclerView.adapter =
            ChatAdapter {
                chat -> chatSelected(chat)
            }
        //Creamos una referencia para la colección de usuarios
        val refUsuarios = db.collection("Usuarios").document(usuario)

        //Del usuario actual, tomamos sus chats
        refUsuarios.collection("chats").get().addOnSuccessListener {
            //Hacemos un cast para que todos los chats pasen a formar parte de una lista de tipo Chat
            chats -> val listChats = chats.toObjects(Chat::class.java)

            //Cuando tengamos la lista la pasamos al Chat adapter para que se cargue en la pantalla de usuario
            (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
        }

        //Para que los chats se actualicen automáticamente y no sea necesario volver a ejecutar la App:
        refUsuarios.collection("chats").addSnapshotListener{
            chats,error ->
                if(error == null){
                    chats?.let{
                        val listChats = it.toObjects(Chat::class.java)
                        (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
                    }
                }
        }
    }

    private fun chatSelected(chat: Chat){
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId",chat.id)
        intent.putExtra("usuario",usuario)
        var usuarioAuth = auth.currentUser?.uid.toString()
        if(chat.nombre == usuario){
            intent.putExtra("usuarioReceptor",chat.usuarios[0])
        }
        else{
            intent.putExtra("usuarioReceptor",chat.usuarios[1])
        }
        //intent.putExtra("usuarioReceptor",chat.nombre)
        startActivity(intent)
    }


}
