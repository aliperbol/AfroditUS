package com.example.tinderus


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Intereses : AppCompatActivity(){

    //Definimos los checkBox relativos a los posibles intereses del usuario
    lateinit var cultural: CheckBox
    lateinit var music: CheckBox
    lateinit var gym: CheckBox
    lateinit var artistmusic: CheckBox
    lateinit var anime: CheckBox
    lateinit var dibujar: CheckBox
    lateinit var leer: CheckBox
    lateinit var fiesta: CheckBox
    lateinit var juegos: CheckBox
    lateinit var modelaje: CheckBox
    lateinit var costura: CheckBox
    lateinit var foto: CheckBox
    lateinit var videojuegos: CheckBox
    lateinit var maqueta: CheckBox
    lateinit var logica: CheckBox
    lateinit var cocinar: CheckBox
    lateinit var jardineria: CheckBox
    lateinit var viajar: CheckBox
    lateinit var voluntariado: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Cambiamos la vista a la de selección de intereses
        setContentView(R.layout.intereses)

        //Mediante el bundle tomaremos los datos enviados desde el activity anterior
        val bundle = intent.extras

        //Tomamos los datos del usuario del registro
        val nombre = bundle?.getString("nombre") ?: ""
        val descripcion = bundle?.getString("descripcion") ?: "no funciona"
        val urlImagen = bundle?.getString("urlImagen") ?: "tampoco funciona"
        val genero = bundle?.getString("genero") ?: ""
        val preferencia = bundle?.getString("preferencia") ?: ""
        val fecha = bundle?.getString("fecha") ?: ""

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


        val buttonClick = findViewById<Button>(R.id.siguiente)

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

        fun guardarUsuarioEnFirebase(){

            //Función que se encarga de registrar el usuario en la base de datos
            val uid = FirebaseAuth.getInstance().uid ?: "" //ID de usuario
            val ref = Firebase.database.getReference()     //Ref a DB
            val us : Map<String, Any> = mapOf(
                "uid" to uid,
                "nombre" to nombre,
                "fotoPerfilURL" to urlImagen,
                "descripcion" to descripcion,
                "genero" to genero,
                "preferencia" to preferencia,
                "edad" to fecha,
                "intereses" to listaGustos(),

            ) //Se trata del usuario en sí

            //En la base de datos, añadiremos los usuarios como nuevos nodos que cuelgan del principal "Usuarios"
            ref.child("Usuarios").child(uid).setValue(us).addOnSuccessListener {
                Toast.makeText(this, "se ha subido el usuario", Toast.LENGTH_SHORT).show()
            }
        }

        //Al hacer click sobre el botón, se registra el usuario en la base de datos
        buttonClick.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            guardarUsuarioEnFirebase()
            startActivity(intent)
        }

    }
}