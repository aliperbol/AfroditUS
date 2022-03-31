package com.example.tinderus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
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

        val buttonClick = findViewById<Button>(R.id.botonHaciaIntereses)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Intereses::class.java)
            startActivity(intent)
        }


    }
}