package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Perfil_propio  : AppCompatActivity() {

    /////////////////////
    //    Variables    //
    /////////////////////

    private val auth = Firebase.auth
    private lateinit var  imagenPerfil:ImageView
    private lateinit var descripcionPerfil:TextView
    private lateinit var interesesPerfil:TextView

    /////////////////////
    //    Funciones    //
    /////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfilpropio)

        //Inicializamos variables
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        descripcionPerfil = findViewById<TextView>(R.id.descripcionPerfil)
        interesesPerfil = findViewById<TextView>(R.id.interesesPerfil)

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

        //Además añadimos dos botones, uno para cerrar sesión y otro para editar el perfil del usuario
        val buttonClick = findViewById<Button>(R.id.cerrarsesion)
        buttonClick.setOnClickListener {
            auth.signOut() //Cerramos sesión
        }
        val buttonClick2 = findViewById<Button>(R.id.editarperfil)
        buttonClick2.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }

        // Una vez se implementan los listener en los botones, debemos incluir la imagen y la descripción
        // del usuario que ha iniciado sesión

        //Obtenemos la imagen de storage de firebase y la ponemos en el perfil del usuario

        val localfile = File.createTempFile("tempImage", "jpj")
        var urlimagen = Firebase.database.getReference("Usuarios").child(auth.currentUser.toString()).child("fotoPerfilURL")
        FirebaseStorage.getInstance().getReferenceFromUrl(urlimagen.toString()).getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imagenPerfil.setImageBitmap(bitmap)
        }

        //Obtenemos la descripción y los gustos del usuario y la incluimos en el perfil
        var usuarioActual = Firebase.database.getReference("Usuarios").child(auth.currentUser?.uid.toString())
            //Colocamos la descripción del usuario
            descripcionPerfil.text = usuarioActual.child("descripcion").toString()

            //Recorremos sus intereses y los incluimos a modo de texto
            var intereses:List<String> = usuarioActual.child("intereses") as List<String>
            var interesesEnTexto: String = ""
            for(interes in intereses){
                if (intereses.indexOf(interes) == (intereses.size -1)){
                    interesesEnTexto.plus(interes)
                }
                else{
                    interesesEnTexto.plus(interes+ ", ")
                }
            }
            interesesPerfil.text = interesesEnTexto
        }
    }
