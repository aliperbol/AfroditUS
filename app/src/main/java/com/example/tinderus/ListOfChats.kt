package com.example.tinderus
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.*

class ListOfChatsActivity : AppCompatActivity() {

    /////////////////////
    //    Variables    //
    /////////////////////

    private var usuario = ""
    private lateinit var botonChatNuevo:View
    private lateinit var textoChat:TextView
    private lateinit var listChatsRecyclerView:RecyclerView
    private var db = Firebase.firestore

    /////////////////////
    //    Funciones    //
    /////////////////////

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)

        //Al cargar la actividad ponemos el layout de la lista de chats
        setContentView(R.layout.activity_list_of_chats)

        //Actualizamos las variables
        botonChatNuevo = findViewById<Button>(R.id.newChatButton)
        textoChat = findViewById<TextView>(R.id.newChatText)
        listChatsRecyclerView = findViewById<RecyclerView>(R.id.listChatsRecyclerView)

        //Obtenemos el nombre de usuario de la vista anterior
        intent.getStringExtra("usuario")?.let{
            usuario = it
        }

        //Comprobamos si este valor es nulo. En caso negativo ejecutamos
        println("Antes de iniciar la comprobación de usuario")
        if(usuario.isNotEmpty()){
            println("Antes de iniciar la vista")
            initViews()
        }
    }

    private fun initViews(){
        //Aplicamos un listener al boton de chat nuevo
        botonChatNuevo.setOnClickListener{
            //Abrimos un nuevo chat
            newChat()
        }

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
        startActivity(intent)
    }

    private fun newChat(){
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
        db.collection("Usuarios").document(usuario).collection("chats").document(chatId).set(chatId)
        db.collection("Usuarios").document(usuarioReceptor).collection("chats").document(chatId).set(chatId)

        //Enviamos a la siguiente pantalla el usuario y el chatId
        val intent = Intent(this,ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }

}
