package com.example.tinderus

import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Perfiles_adapter(val usuarioClick: (Usuario) -> Unit): RecyclerView.Adapter<Perfiles_adapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.cardImage)
        val nombre: TextView = itemView.findViewById(R.id.cardTitle)

    }
    var usuarios: List<Usuario> = emptyList()


    fun setData(list: List<Usuario>){
        usuarios = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usuario_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = usuarios[position].nombre
        //Picasso.get().load(imagenesUsuario[position]).into(holder.imagen)
        val localfile = File.createTempFile("tempImage", "jpg")

        FirebaseStorage.getInstance().getReferenceFromUrl(usuarios[position].fotoPerfil).getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.imagen.setImageBitmap(bitmap)
        }
        holder.imagen.setOnClickListener{
            usuarioClick(usuarios[position])
        }
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }
}