package com.example.tinderus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.indices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class EditarPerfil: AppCompatActivity() {

    private val auth = Firebase.auth
    val usuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(auth.currentUser?.uid.toString())


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarperfil)

        val descripcionCambio = findViewById<EditText>(R.id.IdDescripcion)
        val imagenPropia = findViewById<ImageView>(R.id.imagenPropia)
        val edadPropia = findViewById<EditText>(R.id.edadedita)
        val generoPropio = findViewById<Spinner>(R.id.seleccionGenero)


        val listaGenero = resources.getStringArray(R.array.genero)
        val adaptadorGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaGenero)
        generoPropio.adapter = adaptadorGenero

        imagenPropia.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


        val botonIntereses = findViewById<Button>(R.id.editarinteres)
        botonIntereses.setOnClickListener {
            val intent = Intent(this, Editar_intereses::class.java)
            startActivity(intent)
        }

        val botonGuardar = findViewById<Button>(R.id.guardarcambios)
        botonGuardar.setOnClickListener {
            subirFotoAFirebase()

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
                        imagenPropia.setImageBitmap(bitmap)
                    }

                    //Obtenemos la descripción y los gustos del usuario y la incluimos en el perfil
                    //Colocamos la descripción del usuario
                    descripcionCambio.setText(descripcion, TextView.BufferType.EDITABLE)

                    edadPropia.setText(edad, TextView.BufferType.EDITABLE)

                    generoPropio.setSelection(getIndex(generoPropio, genero));

                    //private method of your class


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("FragmentActivity", "Error: ${databaseError.message}")
                }

            }
            usuario.addListenerForSingleValueEvent(valueEventListener)
        }

        Log.d("FragmentActivity", "Usuario escogido: $usuario")
        tomaDatosUsuario(usuario)


    }

    private fun subirFotoAFirebase() {

        if(fotoSeleccionadaURL == null){
            cambiosGuardados("no")
        }
        else {

            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/FotoPerfil/$filename")
            ref.putFile(fotoSeleccionadaURL!!)
                .addOnSuccessListener {

                    ref.downloadUrl.addOnSuccessListener {
                        cambiosGuardados(it.toString())
                    }.addOnFailureListener {
                        Toast.makeText(this, "No se ha podido subir la imagen", Toast.LENGTH_SHORT).show()

                    }
                }

        }

    }
    private fun cambiosGuardados(imagenURL: String) {
        val edad = findViewById<EditText>(R.id.edadedita).text.toString()
        val descripcion = findViewById<EditText>(R.id.IdDescripcion).text.toString()
        val genero: String = findViewById<Spinner>(R.id.seleccionGenero).selectedItem.toString()

        if(imagenURL!="no"){
            usuario.child("fotoPerfilURL").setValue(imagenURL)
        }


        usuario.child("descripcion").setValue(descripcion)
        usuario.child("edad").setValue(edad)
        usuario.child("genero").setValue(genero)


        val intent = Intent(this, Perfil_propio::class.java)
        startActivity(intent)



    }


    var fotoSeleccionadaURL: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            fotoSeleccionadaURL= data.data

            val imagen  = findViewById<ImageView>(R.id.imagenPropia)
            imagen.setImageURI(fotoSeleccionadaURL)
        }
    }

    fun  getIndex(spinner : Spinner, myString : String) : Int{
        var valor = 0
        for (i in 0..spinner.count-1){

            if (spinner.getItemAtPosition(i).toString() == myString){
                valor= i

            }
        }
        return valor
    }
}