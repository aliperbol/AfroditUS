package com.example.tinderus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Button

class Registro : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        val buttonClick = findViewById<Button>(R.id.BotonEmpezar)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Primer_perfil::class.java)
            startActivity(intent)
        }
    }
}