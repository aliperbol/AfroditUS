package com.example.tinderus

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ChatActivity: AppCompatActivity() {



    /////////////////////
    //    Variables    //
    /////////////////////

    private var chatId = ""
    private var usuario = ""
    private var usuarioreceptor = ""
    private lateinit var botonEnviarMensaje: Button
    private lateinit var mensajeAEnviar: TextView
    private lateinit var messagesRecyclerView:RecyclerView
    private val auth = Firebase.auth
    private var db = Firebase.firestore

    /////////////////////
    //    Funciones    //
    /////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //Tomamos los datos provinentes de las vista anterior
        intent.getStringExtra("chatId")?.let{chatId = it}
        intent.getStringExtra("usuario")?.let{usuario = it}
        intent.getStringExtra("usuarioReceptor")?.let{usuarioreceptor = it}
        Log.d("Fragment", "usuario que soy yo ${usuarioreceptor}")
        var usuarioAuth = auth.currentUser?.uid.toString()
        Log.d("Fragment", "true ${usuarioAuth}")
        mostrar_usuarios(usuarioreceptor)

        //mostrar_usuarios(usuarioreceptor)
        //Tomamos el nombre de usuario del registro
        //Inicializamos las variables
        botonEnviarMensaje = findViewById<Button>(R.id.sendMessageButton)
        mensajeAEnviar = findViewById<TextView>(R.id.messageTextField)
        messagesRecyclerView = findViewById<RecyclerView>(R.id.messagesRecylerView)

        //Comprobamos que estos datos no están vacíos
        if(chatId.isNotEmpty() && usuario.isNotEmpty()){
            initViews()
        }


    }

    private fun initViews(){
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = MessageAdapter(usuario)

        botonEnviarMensaje.setOnClickListener {sendMessage()}

        //Creamos la referencia al chat de la base de datos
        val refChat = db.collection("chats").document(chatId)
        //Accedemos a dicho chat y a los mensajes
        refChat.collection("mensajes").orderBy("dob", Query.Direction.ASCENDING)
        .get()
            .addOnSuccessListener { mensajes ->
                val listMensajes = mensajes.toObjects(Mensaje::class.java)
                //Introducimos los mensajes en el adaptador
                (messagesRecyclerView.adapter as MessageAdapter).setData(listMensajes)
            }

        //Para que sea en tiempo real (no sea necesario reiniciar la app para verlos):
        refChat.collection("mensajes").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener{
            mensajes, error ->
                if(error == null){
                    mensajes?.let{
                        val listMensajes = mensajes.toObjects(Mensaje::class.java)
                        (messagesRecyclerView.adapter as MessageAdapter).setData(listMensajes)
                    }
                }
        }
    }

    private fun sendMessage(){

        //Creamos una variable que identifique el mensaje
        val mensaje = Mensaje(
            mensaje = mensajeAEnviar.text.toString(),
            de = usuario
        )

        //Guardamos el mensaje en la base de datos
        db.collection("chats").document(chatId).collection("mensajes").document().set(mensaje)
        mensajeAEnviar.setText("")
    }

    private fun mostrar_usuarios( uidChat : String) {
        val datos = Firebase.database.getReference("Usuarios")
        var usuario = mutableListOf<String>()
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            //Cada vez que cambie algún dato, se modificará directamente en la pagina
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val uid = ds.child("uid").getValue(String::class.java) ?: ""
                    if (uid == uidChat) {
                        val nombre = ds.child("nombre").getValue(String::class.java) ?: ""
                        val imagen = ds.child("fotoPerfilURL").getValue(String::class.java) ?: ""
                        Log.d("FragmentActivity","nombre ${nombre}")
                        title= nombre

                        /*val localfile = File.createTempFile("tempImage", "jpg")

                        FirebaseStorage.getInstance().getReferenceFromUrl(imagen).getFile(localfile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                            //fotoPerfilVista.setImageBitmap(bitmap)
                        }*/
                    }

                }

                //De esta forma conseguimos accedemos a nuestro recycledView para mostrar usuarios como grid
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("FragmentActivity", "Error: ${databaseError.message}")
            }

        }
        datos.addListenerForSingleValueEvent(valueEventListener)
    }

}