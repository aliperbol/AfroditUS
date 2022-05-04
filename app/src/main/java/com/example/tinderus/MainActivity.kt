package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    //Creamos un contador para que se deba pulsar dos veces el boton atrás para salir de la app
    var contadorSalir = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.iniciosesion)
        buttonClick.setOnClickListener {
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
        }
        val buttonClick2 = findViewById<Button>(R.id.registro)
        buttonClick2.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
    }

    override fun onBackPressed() {

        if(contadorSalir == 0){
            Toast.makeText(this,"Pulse de nuevo para salir", Toast.LENGTH_SHORT)
            contadorSalir+=1
        }
        else{
            var intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                contadorSalir = 0
            }
        }.start()
    }

}