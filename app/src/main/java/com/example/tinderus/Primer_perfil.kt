package com.example.tinderus

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.BufferedWriter
import java.io.FileWriter
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern


class Primer_perfil:AppCompatActivity() {
    var username = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.primer_perfil);

        //Recogemos los datos enviados en la actividad anterior
        val bundle = intent.extras
        //Tomamos el nombre de usuario del registro
        username = bundle?.getString("username") ?: ""
        findViewById<TextView>(R.id.nombreUsuario).text = "¡Hola, " +  username + "!"

        //Seleccionar tu genero
        val spinnerGenero = findViewById<Spinner>(R.id.seleccionGenero)

        //Lista de generos
        val listaGenero = resources.getStringArray(R.array.genero)

        //Objeto completo generos
        val adaptadorGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaGenero)
        spinnerGenero.adapter = adaptadorGenero

        //Seleccionar la preferencia sexual
        val spinnerPreferencias = findViewById<Spinner>(R.id.seleccionPref)

        //Lista de preferencias
        val listaPreferencias = resources.getStringArray(R.array.preferencias)

        //Objeto completo preferencias
        val adaptadorPreferencias = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPreferencias)
        spinnerPreferencias.adapter = adaptadorPreferencias


        //Valores que pasamos a la siguiente pagina para subirlo a la DB

        val buttonClick = findViewById<Button>(R.id.botonHaciaIntereses)
        buttonClick.setOnClickListener {
            subirFotoAFirebase()
        }
        //Colocamos un onclick sobre la imagen para porder subir el archivo
        findViewById<ImageView>(R.id.selectImage).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    private fun subirFotoAFirebase() {

        if(fotoSeleccionadaURL == null){
            Toast.makeText(this, "fallo", Toast.LENGTH_SHORT).show()
        }
        else {

            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/FotoPerfil/$filename")
            ref.putFile(fotoSeleccionadaURL!!)
                .addOnSuccessListener {

                    ref.downloadUrl.addOnSuccessListener {
                            haciaIntereses(it.toString())
                    }.addOnFailureListener {
                        Toast.makeText(this, "No se ha podido subir la imagen", Toast.LENGTH_SHORT).show()

                    }
                }

        }

    }
    private fun haciaIntereses(imagenURL: String){
        val nombre: String = findViewById<TextView>(R.id.nombreUsuario).text.toString()
        val descripcion = findViewById<EditText>(R.id.IdDescripcion).text.toString()
        val genero: String = findViewById<Spinner>(R.id.seleccionGenero).selectedItem.toString()
        val preferencia: String = findViewById<Spinner>(R.id.seleccionPref).selectedItem.toString()
        val fechaNacimiento: String = findViewById<EditText>(R.id.edad).text.toString()
        fun getPattern() ="""\d{2}"""
        var fechaCorrecta = false

            val intent = Intent(this, Intereses::class.java).apply {
                if(!fechaNacimiento.matches(getPattern().toRegex())){
                    Toast.makeText(this@Primer_perfil, "Formato de fecha erróneo", Toast.LENGTH_SHORT).show()
                }else{
                    putExtra("urlImagen", imagenURL)
                    putExtra("fecha", fechaNacimiento)
                    putExtra("nombre", username)
                    putExtra("descripcion", descripcion)
                    putExtra("genero", genero)
                    putExtra("preferencia", preferencia)
                    fechaCorrecta=true
                }
            }
        if (fechaCorrecta){
            startActivity(intent)
        }

    }


    //Declaramos la variable que almacenará la foto
    var fotoSeleccionadaURL: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            fotoSeleccionadaURL= data.data

            val imagen  = findViewById<ImageView>(R.id.selectImage)
            imagen.setImageURI(fotoSeleccionadaURL)
        }
    }



}
