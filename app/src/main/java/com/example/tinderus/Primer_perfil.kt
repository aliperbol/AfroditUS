package com.example.tinderus

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.provider.MediaStore
import android.net.Uri
import android.widget.*


class Primer_perfil:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.primer_perfil);

        //Recogemos los datos enviados en la actividad anterior
        val bundle = intent.extras
        //Tomamos el nombre de usuario del registro
        val username = bundle?.getString("username")

        findViewById<TextView>(R.id.nombreUsuario).text = username

        //Seleccionar la preferencia sexual
        val spinner = findViewById<Spinner>(R.id.seleccionPref)

        //Lista de preferencias
        val lista = resources.getStringArray(R.array.preferencias)

        //Objeto completo preferencias
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador

        //Onclick para finalizar y conducir a intereses
        val buttonClick = findViewById<Button>(R.id.botonHaciaIntereses)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Intereses::class.java)
            startActivity(intent)
        }

        //Colocamos un onclick sobre la imagen para porder subir el archivo
        findViewById<ImageView>(R.id.selectImage).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    //Declaramos la variable que almacenará la foto
    var fotoSeleccionadaURL: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            fotoSeleccionadaURL= data.data
            //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fotoSeleccionadaURL)
            //val bitmapDrawable = BitmapDrawable(bitmap)

            //findViewById<Button>(R.id.selectImage).setBackgroundDrawable(bitmapDrawable)
            val imagen  = findViewById<ImageView>(R.id.selectImage)
            imagen.setImageURI(fotoSeleccionadaURL)
        }


    }

}