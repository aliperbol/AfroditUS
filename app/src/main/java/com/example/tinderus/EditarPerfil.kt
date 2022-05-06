package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditarPerfil: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarperfil)

        val buttonClick = findViewById<Button>(R.id.editarinteres)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Editar_intereses::class.java)
            startActivity(intent)
        }
        val buttonClick2 = findViewById<Button>(R.id.cambiarcontra)
        buttonClick2.setOnClickListener {
            val intent = Intent(this, Cambiar_contra::class.java)
            startActivity(intent)
        }
        val buttonClick3 = findViewById<Button>(R.id.guardarcambios)
        buttonClick3.setOnClickListener {
            val intent = Intent(this, Perfil_propio::class.java)
            startActivity(intent)
        }
        val spinnerGenero = findViewById<Spinner>(R.id.seleccionGenero)

    }
}