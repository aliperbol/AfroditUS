package com.example.tinderus

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView


class Primer_perfil:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.primer_perfil);

        val bundle = intent.extras
        val username = bundle?.getString("username")

        findViewById<TextView>(R.id.nombreUsuario).text = username

        val spinner = findViewById<Spinner>(R.id.seleccionPref)

        val lista = resources.getStringArray(R.array.preferencias)

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador

        val spinner1 = findViewById<Spinner>(R.id.seleccionGen)

        val lista1 = resources.getStringArray(R.array.genero)

        val adaptador1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista1)
        spinner1.adapter = adaptador1

        val buttonClick = findViewById<Button>(R.id.botonHaciaIntereses)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Intereses::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.selectImage).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    var fotoSeleccionadaURL: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            fotoSeleccionadaURL= data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fotoSeleccionadaURL)
            val bitmapDrawable = BitmapDrawable(bitmap)

            findViewById<Button>(R.id.selectImage).setBackgroundDrawable(bitmapDrawable)
        }


    }

}