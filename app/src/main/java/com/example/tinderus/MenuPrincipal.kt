package com.example.tinderus

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        mostrar_usuarios()


        findViewById<Button>(R.id.botonCierreSesion).setOnClickListener{
            Firebase.auth.signOut()
            onBackPressed() }


    }
    fun mostrar_usuarios(){
        val datos = Firebase.database.getReference("Usuarios")

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuarios = ArrayList<Pair<String?, String?>>()
                for (ds in dataSnapshot.children) {
                    val name = ds.child("nombre").getValue(String::class.java)
                    val imagen = ds.child("fotoPerfilURL").getValue(String::class.java)
                    findViewById<TextView>(R.id.username1).text = name

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


}