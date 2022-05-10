package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class Editar_intereses : AppCompatActivity() {

    /////////////////////
    //    Variables    //
    /////////////////////

    private val auth = Firebase.auth
    val usuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(auth.currentUser?.uid.toString())

    /////////////////////
    //    Funciones    //
    /////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarintereses)

        //Asignamos valores a cada variable definida anteriormente
        var cultural = findViewById<CheckBox>(R.id.cultural)
        var music = findViewById<CheckBox>(R.id.music)
        var gym = findViewById<CheckBox>(R.id.gym)
        var artistmusic = findViewById<CheckBox>(R.id.artistmusic)
        var anime = findViewById<CheckBox>(R.id.anime)
        var dibujar = findViewById<CheckBox>(R.id.dibujar)
        var leer = findViewById<CheckBox>(R.id.leer)
        var fiesta = findViewById<CheckBox>(R.id.fiesta)
        var juegos = findViewById<CheckBox>(R.id.juegos)
        var foto = findViewById<CheckBox>(R.id.foto)
        var modelaje = findViewById<CheckBox>(R.id.modelaje)
        var costura = findViewById<CheckBox>(R.id.costura)
        var videojuegos = findViewById<CheckBox>(R.id.videojuegos)
        var maqueta = findViewById<CheckBox>(R.id.maqueta)
        var logica = findViewById<CheckBox>(R.id.logica)
        var cocinar = findViewById<CheckBox>(R.id.cocinar)
        var jardineria = findViewById<CheckBox>(R.id.jardineria)
        var viajar = findViewById<CheckBox>(R.id.viajar)
        var voluntariado = findViewById<CheckBox>(R.id.voluntariado)

        fun listaGustos(): List<String>{

            val listaGustos = mutableListOf<String>()
            if(cultural.isChecked){
                listaGustos.add(cultural.getText().toString())
            }
            if(music.isChecked){
                listaGustos.add(music.getText().toString())
            }
            if(gym.isChecked){
                listaGustos.add(gym.getText().toString())
            }
            if(artistmusic.isChecked){
                listaGustos.add(artistmusic.getText().toString())
            }
            if(anime.isChecked){
                listaGustos.add(anime.getText().toString())
            }
            if(dibujar.isChecked){
                listaGustos.add(dibujar.getText().toString())
            }
            if(leer.isChecked){
                listaGustos.add(leer.getText().toString())
            }
            if(fiesta.isChecked){
                listaGustos.add(fiesta.getText().toString())
            }
            if(juegos.isChecked){
                listaGustos.add(juegos.getText().toString())
            }
            if(foto.isChecked){
                listaGustos.add(foto.getText().toString())
            }
            if(modelaje.isChecked){
                listaGustos.add(modelaje.getText().toString())
            }
            if(costura.isChecked){
                listaGustos.add(costura.getText().toString())
            }
            if(videojuegos.isChecked){
                listaGustos.add(videojuegos.getText().toString())
            }
            if(maqueta.isChecked){
                listaGustos.add(maqueta.getText().toString())
            }
            if(logica.isChecked){
                listaGustos.add(logica.getText().toString())
            }
            if(cocinar.isChecked){
                listaGustos.add(cocinar.getText().toString())
            }
            if(jardineria.isChecked){
                listaGustos.add(jardineria.getText().toString())
            }
            if(viajar.isChecked){
                listaGustos.add(viajar.getText().toString())
            }
            if(voluntariado.isChecked){
                listaGustos.add(voluntariado.getText().toString())
            }
            return listaGustos

        }

        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            var interesesModif = listaGustos()
            usuario.child("intereses").setValue(interesesModif)
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }
    }

}