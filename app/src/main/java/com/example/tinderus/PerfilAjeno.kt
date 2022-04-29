package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

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
        val intereses = bundle?.getString("intereses")?: ""
        val fotoPerfil = bundle?.getString("fotoPerfil")?: ""
        title=nombre

        val descripcionVista = findViewById<TextView>(R.id.descripcionajena)
        val fotoPerfilVista = findViewById<ImageView>(R.id.imagenajena)
        val edadVista=findViewById<TextView>(R.id.edadajena)
        val localfile = File.createTempFile("tempImage", "jpg")

        FirebaseStorage.getInstance().getReferenceFromUrl(fotoPerfil).getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            fotoPerfilVista.setImageBitmap(bitmap)
        }
        edadVista.text =edad
        descripcionVista.text =descripcion






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





    }
}