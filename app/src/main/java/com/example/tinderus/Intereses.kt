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

    var contador= 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intereses)

        val bundle = intent.extras
        //Tomamos el nombre de usuario del registro
        val nombre = bundle?.getString("nombre") ?: ""
        val descripcion = bundle?.getString("descripcion") ?: ""
        val urlImagen = bundle?.getString("urlImagen") ?: ""
        val genero = bundle?.getString("genero") ?: ""
        val preferencia = bundle?.getString("preferencia") ?: ""
        val fecha = bundle?.getString("fecha") ?: ""


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

       /**if(anime.isChecked){

           anime.setChecked(true)

           contador += 1
           println("+1")

        }else if (cultural.isChecked == true){
            contador= contador + 1
        }else if (music.isChecked == true){
            contador= contador + 1
            println("+1")
        }else if (gym.isChecked == true){
            contador+=1
        }else if (artistmusic.isChecked == true){
            contador+=1
        }else if (dibujar.isChecked == true){
            contador+=1
        }else if (leer.isChecked == true){
            contador+=1
        }else if (fiesta.isChecked == true){
            contador+=1
        }else if (juegos.isChecked == true){
            contador+=1
        }else if (modelaje.isChecked == true){
            contador+=1
        }else if (costura.isChecked == true){
            contador+=1
        }else if (foto.isChecked == true){
            contador+=1
        }else if (videojuegos.isChecked == true){
            contador+=1
        }else if (maqueta.isChecked == true){
            contador+=1
        }else if (videojuegos.isChecked == true){
            contador+=1
        }else if (logica.isChecked == true){
            contador+=1
        }else if (cocinar.isChecked == true){
            contador+=1
        }else if (jardineria.isChecked == true){
            contador+=1
        }else if (voluntariado.isChecked == true){
            contador+=1
        }else {
            contador-=1

        }**/



        fun guardarUsuarioEnFirebase(){

            val uid = FirebaseAuth.getInstance().uid ?: ""
            val ref = Firebase.database.getReference()
            val us : Map<String, String> = mapOf(
                "uid" to uid,
                "nombre" to nombre,
                "fotoPerfilURL" to urlImagen,
                "descripcion" to descripcion,
                "genero" to genero,
                "preferencia" to preferencia,
                "fechaNacimiento" to fecha

            )

            ref.child("Usuarios").child(uid).setValue(us).addOnSuccessListener {
                Toast.makeText(this, "se ha subido el usuario", Toast.LENGTH_SHORT).show()

            }
        }


        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            guardarUsuarioEnFirebase()
            startActivity(intent)
        }

    }
}