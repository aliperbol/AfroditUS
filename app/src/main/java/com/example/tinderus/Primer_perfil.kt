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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.primer_perfil);

        //Recogemos los datos enviados en la actividad anterior
        val bundle = intent.extras
        //Tomamos el nombre de usuario del registro
        val username = bundle?.getString("username")
        findViewById<TextView>(R.id.nombreUsuario).text = username

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
        val nombre: String = findViewById<TextView>(R.id.nombreUsuario).text.toString()
        val descripcion = findViewById<EditText>(R.id.IdDescripcion).text.toString()
        val genero: String = findViewById<Spinner>(R.id.seleccionGenero).selectedItem.toString()
        val preferencia: String = findViewById<Spinner>(R.id.seleccionPref).selectedItem.toString()
        val fechaNacimiento: String = findViewById<EditText>(R.id.fechaNacimiento).text.toString()
        fun getPattern() = """\d{2}\.\d{2}\.\d{4}"""


        //Onclick para finalizar y conducir a intereses
        val buttonClick = findViewById<Button>(R.id.botonHaciaIntereses)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Intereses::class.java).apply {
                if(!fechaNacimiento.matches(getPattern().toRegex())){
                    Toast.makeText(this@Primer_perfil, "Formato de fecha erróneo", Toast.LENGTH_SHORT).show()
                }else{
                    putExtra("urlImagen", subirFotoAFirebase())
                    putExtra("fecha", fechaNacimiento)
                    putExtra("nombre", nombre)
                    putExtra("descripcion", descripcion)
                    putExtra("genero", genero)
                    putExtra("preferencia", preferencia)
                }

            }


            startActivity(intent)

        }

        //Colocamos un onclick sobre la imagen para porder subir el archivo
        findViewById<ImageView>(R.id.selectImage).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    private fun subirFotoAFirebase(): String {
        if(fotoSeleccionadaURL == null){
            return "null"
        }
        else {
            val filename = UUID.randomUUID().toString()
            var imagen = ""
            val ref = FirebaseStorage.getInstance().getReference("/FotoPerfil/$filename")
            ref.putFile(fotoSeleccionadaURL!!)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "Successfully upload image: ${it.metadata?.path}}")

                    ref.downloadUrl.addOnSuccessListener {
                        Toast.makeText(this, "Procede a subir usuario", Toast.LENGTH_SHORT).show()
                        imagen = it.toString()
                    }.addOnFailureListener {
                        Toast.makeText(this, "No se ha podido subir el usuario", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            return imagen
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
