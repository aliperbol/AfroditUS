package com.example.tinderus

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import java.io.File

class ChatAdapter(val chatClick: (Chat) -> Unit): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    /////////////////////
    //    Variables    //
    /////////////////////

    var chats: List<Chat> = emptyList()
    private val auth = Firebase.auth
    /////////////////////
    //    Funciones    //
    /////////////////////

    fun setData(list: List<Chat>){
        chats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position:Int){

        //holder.itemView.findViewById<TextView>(R.id.chatNameText).text = chats[position].nombre

        if(auth.currentUser?.uid.toString() == chats[position].nombre) {
            //if(chats[position].nombre == chats[position].usuarios[1]) {
            mostrar_usuarios(chats[position].usuarios[0], holder)
            //holder.itemView.findViewById<TextView>(R.id.usersTextView).text = chats[position].usuarios.toString()

            holder.itemView.setOnClickListener {
                chatClick(chats[position])
            }
        }
        else{
            mostrar_usuarios(chats[position].usuarios[1], holder)
            //holder.itemView.findViewById<TextView>(R.id.usersTextView).text = chats[position].usuarios.toString()

            holder.itemView.setOnClickListener {
                chatClick(chats[position])
            }
        }


    }

    override fun getItemCount():Int{
        return chats.size
    }

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private fun mostrar_usuarios( uidChat : String, holder: ChatViewHolder){
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
                        usuario.add(nombre)
                        usuario.add(imagen)
                        Log.d("FragmentActivity", "nombre $nombre")

                        holder.itemView.findViewById<TextView>(R.id.chatNameText).text = nombre

                        val fotoPerfilVista = holder.itemView.findViewById<ImageView>(R.id.imgFoto)

                        val localfile = File.createTempFile("tempImage", "jpg")

                        FirebaseStorage.getInstance().getReferenceFromUrl(imagen).getFile(localfile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                            fotoPerfilVista.setImageBitmap(bitmap)
                        }
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