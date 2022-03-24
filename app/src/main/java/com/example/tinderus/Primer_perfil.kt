package com.example.tinderus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

enum class ProviderType{
    BASIC
}
class Primer_perfil:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.example.tinderus.R.layout.primer_perfil);

        val spinner= findViewById<Spinner>(R.id.seleccionPref)

        val lista= resources.getStringArray(R.array.preferencias)

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador



    }
}