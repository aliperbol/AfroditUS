package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MenuPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)


        //Asignación de valores a variables
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")

        //Al iniciar la app, estableceremos que los botones de la barra de menu principal tengan
        //un onclick preestablecido

        val chatButton = findViewById<ImageView>(R.id.imgChats)
        chatButton.setOnClickListener{
            val intent = Intent(this,ListOfChatsActivity::class.java)
            startActivity(intent)
        }

        val initButton = findViewById<ImageView>(R.id.imgInicio)
        initButton.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        mostrar_usuarios()
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        findViewById<TextView>(R.id.email_perfil).text = email
        findViewById<TextView>(R.id.provider_perfil).text = provider

        findViewById<Button>(R.id.botonCierreSesion).setOnClickListener {
            Firebase.auth.signOut()
            onBackPressed()
        }
    }

    private fun mostrar_usuarios(){
        val datos = Firebase.database.getReference("Usuarios")

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuarios = ArrayList<Pair<String?, String?>>()
                for (ds in dataSnapshot.children) {
                    val name = ds.child("nombre").getValue(String::class.java)
                    val imagen = ds.child("fotoPerfilURL").getValue(String::class.java)
                    //findViewById<TextView>(R.id.username1).text = name

                    val par = Pair(name, imagen)
                    usuarios.add(par)
                    Log.d("FragmentActivity", "usernames : $name")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("FragmentActivity", "Error: ${databaseError.message}")
            }

        }
        datos.addListenerForSingleValueEvent(valueEventListener)




    }

    private fun messages(){
        //Función que lleva al usuario hasta su lista de chats
        val intent = Intent(this,ChatActivity::class.java)
        startActivity(intent)
    }

}