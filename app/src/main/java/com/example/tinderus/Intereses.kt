package com.example.tinderus


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox

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
       /** if((cultural || music || gym || artistmusic || anime || dibujar || leer || fiesta || juegos || modelaje ||costura || foto || videojuegos ||
                    maqueta ||logica || cocinar || jardineria || viajar || voluntariado).isChecked){

        }
        if(anime.isChecked){
            contador+=1
        }
        else if (anime.isChecked == false){
            contador+=-1
        }

        buttonClick.isEnabled = false

        if(contador >=3 ){
            buttonClick.isEnabled = true
        }**/
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }
}