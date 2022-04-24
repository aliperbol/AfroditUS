package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class MenuPrincipal : AppCompatActivity() {

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)


        //Al iniciar la app, estableceremos que los botones de la barra de menu principal tengan
        //un onclick preestablecido

        val chatButton = findViewById<ImageView>(R.id.imgChats)
        chatButton.setOnClickListener{

            val intent = Intent(this,ListOfChats::class.java)
            intent.putExtra("usuario", auth.currentUser?.uid)
            startActivity(intent)


        }

        val initButton = findViewById<ImageView>(R.id.imgInicio)
        initButton.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        val perfilButton = findViewById<ImageView>(R.id.imgPerfil)
        perfilButton.setOnClickListener{
            val intent = Intent(this, Perfil_propio::class.java)
            startActivity(intent)
        }

        mostrar_usuarios()
    }

    //que aparezca los tres puntitos
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //opciones de filtros

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.edad -> (R.id.edad)
        }
        return super.onOptionsItemSelected(item)
    }




    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun setup() {
        title = "Inicio"


        /*findViewById<Button>(R.id.botonCierreSesion).setOnClickListener {
            Firebase.auth.signOut()
            onBackPressed()
        }*/
    }

    private fun mostrar_usuarios(){
        val datos = Firebase.database.getReference("Usuarios")

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            //Cada vez que cambie algún dato, se modificará directamente en la pagina
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuarios = ArrayList<Pair<String?, String?>>()
                for (ds in dataSnapshot.children) {
                    val name = ds.child("nombre").getValue(String::class.java)
                    val imagen = ds.child("fotoPerfilURL").getValue(String::class.java)


                    val par = Pair(name, imagen)
                    usuarios.add(par)
                    Log.d("FragmentActivity", "usernames : $name")
                }
                findViewById<TextView>(R.id.username1).text = usuarios[0].first

                //Obtenemos la imagen de storage de firebase y la ponemos en el perfil del usuario
                val localfile = File.createTempFile("tempImage", "jpj")
                var imagen = usuarios[0].second
                FirebaseStorage.getInstance().getReferenceFromUrl(imagen ?: "").getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    findViewById<ImageView>(R.id.userale1).setImageBitmap(bitmap)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("FragmentActivity", "Error: ${databaseError.message}")
            }

        }
        datos.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun messages(){
        val currentUser = auth.currentUser
        //Función que lleva al usuario hasta su lista de chats
        val intent = Intent(this,ListOfChats::class.java)
        intent.putExtra("usuario", currentUser?.email)
        startActivity(intent)
        finish()
    }


}