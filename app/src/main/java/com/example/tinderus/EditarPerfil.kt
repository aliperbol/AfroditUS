package com.example.tinderus

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class EditarPerfil: AppCompatActivity() {
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarperfil)

        val botonIntereses = findViewById<Button>(R.id.editarinteres)
        botonIntereses.setOnClickListener {
            val intent = Intent(this, Editar_intereses::class.java)
            startActivity(intent)
        }

        val botonGuardar = findViewById<Button>(R.id.guardarcambios)
        botonGuardar.setOnClickListener {

            val descripcionCambio = findViewById<EditText>(R.id.IdDescripcion)
            val intent = Intent(this, Perfil_propio::class.java)
            startActivity(intent)
        }

        fun tomaDatosUsuario(usuario: DatabaseReference){
            // Una vez se implementan los listener en los botones, debemos incluir la imagen y la descripción
            // del usuario que ha iniciado sesión

            val valueEventListener: ValueEventListener = object : ValueEventListener {


                //Cada vez que cambie algún dato, se modificará directamente en la pagina
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    //Inicializamos las variables con los datos que tomamos del usuario
                    val nombre = dataSnapshot.child("nombre").getValue(String::class.java) ?:""
                    val edad = dataSnapshot.child("edad").getValue(String::class.java)?:""
                    val descripcion = dataSnapshot.child("descripcion").getValue(String::class.java)?:""
                    val preferencia = dataSnapshot.child("preferencia").getValue(String::class.java)?:""
                    val genero = dataSnapshot.child("genero").getValue(String::class.java)?:""
                    val uid = dataSnapshot.child("uid").getValue(String::class.java)?:""
                    val imagen = dataSnapshot.child("fotoPerfilURL").getValue(String::class.java)?:""
                    var intereses = ArrayList<String>()
                    Log.d("FragmentActivity", "Error: $imagen")
                    title=nombre

                    for (i in dataSnapshot.child("intereses").children){
                        intereses.add(i.getValue(String::class.java)?:"")
                    }


                    //De esta forma conseguimos accedemos a nuestro recycledView para mostrar usuarios como grid


                    //Obtenemos la imagen de storage de firebase y la ponemos en el perfil del usuario

                    val localfile = File.createTempFile("tempImage", "jpj")
                    FirebaseStorage.getInstance().getReferenceFromUrl(imagen).getFile(localfile).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                        imagenPerfil.setImageBitmap(bitmap)
                    }

                    //Obtenemos la descripción y los gustos del usuario y la incluimos en el perfil
                    //Colocamos la descripción del usuario
                    descripcionPerfil.text = descripcion

                    //Recorremos sus intereses y los incluimos a modo de texto

                    var interesesEnTexto: String = ""
                    for(interes in intereses){
                        if (intereses.indexOf(interes) == (intereses.size -1)){
                            interesesEnTexto += interes
                        }
                        else{
                            interesesEnTexto += interes+ ", "
                        }
                    }
                    interesesPerfil.text = interesesEnTexto

                    //Cambiamos el nombre del perfil
                    //nombrePerfil.text = nombre
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("FragmentActivity", "Error: ${databaseError.message}")
                }

            }
            usuario.addListenerForSingleValueEvent(valueEventListener)
        }

        val usuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(auth.currentUser?.uid.toString())
        Log.d("FragmentActivity", "Usuario escogido: $usuario")
        tomaDatosUsuario(usuario)


    }
}