package com.example.tinderus


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
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
        cultural = findViewById(R.id.cultural)
        music = findViewById(R.id.music)
        gym = findViewById(R.id.gym)
        artistmusic = findViewById(R.id.artistmusic)
        anime = findViewById(R.id.anime)
        dibujar = findViewById(R.id.dibujar)
        leer = findViewById(R.id.leer)
        fiesta = findViewById(R.id.fiesta)
        juegos = findViewById(R.id.juegos)
        foto = findViewById(R.id.foto)
        modelaje = findViewById(R.id.modelaje)
        costura = findViewById(R.id.costura)
        videojuegos = findViewById(R.id.videojuegos)
        maqueta = findViewById(R.id.maqueta)
        logica = findViewById(R.id.logica)
        cocinar = findViewById(R.id.cocinar)
        jardineria = findViewById(R.id.jardineria)
        viajar = findViewById(R.id.viajar)
        voluntariado = findViewById(R.id.voluntariado)
        val buttonClick = findViewById<Button>(R.id.siguiente)

        fun guardarUsuarioEnFirebase(){

            //Función que se encarga de registrar el usuario en la base de datos
            val uid = FirebaseAuth.getInstance().uid ?: "" //ID de usuario
            val ref = Firebase.database.getReference()     //Ref a DB
            val us : Map<String, String> = mapOf(
                "uid" to uid,
                "nombre" to nombre,
                "fotoPerfilURL" to urlImagen,
                "descripcion" to descripcion,
                "genero" to genero,
                "preferencia" to preferencia,
                "fechaNacimiento" to fecha

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