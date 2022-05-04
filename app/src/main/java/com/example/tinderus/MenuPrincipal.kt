package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


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
            R.id.edad -> floatingWindowEdad()
            R.id.gener -> floatingWindowGenero()
            R.id.inter -> floatingWindowIntereses()
        }
        return super.onOptionsItemSelected(item)
    }


    //ventana flotante edad
    fun floatingWindowEdad() {
        //crear ventana
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Filtrar por:")
        builder.setMessage(" ")
        //llamas al layaout
        val dialogLayout = inflater.inflate(R.layout.edad_filtro, null)

        val edMin  = findViewById<EditText>(R.id.edMin)
        val edMax = findViewById<EditText>(R.id.edMax)
        builder.setView(dialogLayout)
        //acción a realizar tras pultar botón OK, no funcionan los toast :(
        builder.setPositiveButton("OK") { dialog, which -> Toast.makeText(applicationContext,  "EditText is lalal", Toast.LENGTH_SHORT).show()
            //dialogInterface, i -> Toast.makeText(applicationContext, "EditText is lalal", Toast.LENGTH_SHORT).show()
        }
        //boton NO
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()}
        //mostrar builder
        builder.show()
    }

    //ventana flotante genero
    fun floatingWindowGenero(){
        //lista de opciones
        val items = arrayOf("Hombre", "Mujer")
        val selectedList = ArrayList<Int>()
        //crear ventana flotante
        val builder = AlertDialog.Builder(this)
        //titulo
        builder.setTitle("Filtrar por:")
        //las opciones en formato de multilista
        builder.setMultiChoiceItems(items, null
        ) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }
        //botón OK
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }

            Toast.makeText(applicationContext, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }
        //boton NO
        builder.setNegativeButton(android.R.string.no) { dialog, which -> Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

    //ventana flotante intereses
    fun floatingWindowIntereses(){
        //lista de opciones
        val items = arrayOf("Ir a eventos culturales", "Escuchar música", "Ver peliculas/series", "Ir al gimnasio", "Hacer música",
            "Anime", "Dibujar", "Leer", "Ir de fiesta", "Juegos de mesa", "Modelaje", "Costura", "Fotografía",
            "Jugar videojuegos", "Hacer maquetas", "Retos lógicos", "Cocinar", "Jardinería", "Viajar", "Voluntariado")
        val selectedList = ArrayList<Int>()
        //crear ventana flotante
        val builder = AlertDialog.Builder(this)
        //titulo
        builder.setTitle("Filtrar por:")
        //las opciones en formato de multilista
        builder.setMultiChoiceItems(items, null
        ) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }
        //botón OK
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }
            Toast.makeText(applicationContext, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
        }
        //boton NO
        builder.setNegativeButton(android.R.string.no) { dialog, which -> Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show() }
        builder.show()

    }


    private fun mostrar_usuarios(){
        val datos = Firebase.database.getReference("Usuarios")
        val listadoUsuarioRecyclerView =findViewById<RecyclerView>(R.id.imagenesPerfiles)
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            //Cada vez que cambie algún dato, se modificará directamente en la pagina
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuarios = ArrayList<Usuario>()

                var preferenciaUsuarioActual = ""
                var generoUsuarioActual = ""
                for (ds in dataSnapshot.children) {
                    val uid = ds.child("uid").getValue(String::class.java) ?: ""

                    if(uid== auth.currentUser?.uid.toString()){
                        val preferencia = ds.child("preferencia").getValue(String::class.java) ?: ""
                        val genero = ds.child("genero").getValue(String::class.java) ?: ""
                        preferenciaUsuarioActual = preferencia
                        generoUsuarioActual = genero

                    }

                }
<<<<<<< HEAD
                   for (ds in dataSnapshot.children) {

                       val uid = ds.child("uid").getValue(String::class.java) ?: ""
                       if (uid != auth.currentUser?.uid.toString()) {
                           //para que no salga nuestro propio perfil en el menu principal

                           val nombre = ds.child("nombre").getValue(String::class.java) ?: ""
                           val edad = ds.child("edad").getValue(String::class.java) ?: ""
                           val descripcion =
                               ds.child("descripcion").getValue(String::class.java) ?: ""
                           val preferencia =
                               ds.child("preferencia").getValue(String::class.java) ?: ""
                           val genero = ds.child("genero").getValue(String::class.java) ?: ""

                           val imagen = ds.child("fotoPerfilURL").getValue(String::class.java) ?: ""
                           var intereses = ArrayList<String>()

                           for (i in ds.child("intereses").children) {
                               intereses.add(i.getValue().toString())
                           }


                           //Filtramos los usuarios que aparecen en funcion de la preferencia sexual

                           if (preferenciaUsuarioActual == "Hombres" && genero == "Hombre" && (preferencia=="Hombres" || preferencia == "No me importa") && generoUsuarioActual=="Hombre") {

                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }
                           if (preferenciaUsuarioActual == "Mujeres" && genero == "Mujer" && (preferencia=="Hombres" || preferencia == "No me importa") && generoUsuarioActual=="Hombre") {
                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }

                           if (preferenciaUsuarioActual == "Mujeres" && genero == "Mujer" && (preferencia=="Mujeres" || preferencia == "No me importa") && generoUsuarioActual=="Mujer") {

                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }

                           if (preferenciaUsuarioActual == "Hombres" && genero == "Hombre" && (preferencia=="Mujeres" || preferencia == "No me importa") && generoUsuarioActual=="Mujer") {

                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }

                           if (preferenciaUsuarioActual == "No me importa" && (preferencia=="Mujeres" || preferencia == "No me importa") && generoUsuarioActual=="Mujer") {

                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }

                           if (preferenciaUsuarioActual == "No me importa" && (preferencia=="Hombres" || preferencia == "No me importa") && generoUsuarioActual=="Hombre") {

                               usuarios.add(
                                   Usuario(
                                       nombre,
                                       edad,
                                       descripcion,
                                       preferencia,
                                       genero,
                                       uid,
                                       imagen,
                                       intereses
                                   )
                               )
                           }
                       }
                   }
=======
                for (ds in dataSnapshot.children) {

                    val uid = ds.child("uid").getValue(String::class.java) ?: ""
                    if (uid != auth.currentUser?.uid.toString()) {
                        //para que no salga nuestro propio perfil en el menu principal

                        val nombre = ds.child("nombre").getValue(String::class.java) ?: ""
                        val edad = ds.child("edad").getValue(String::class.java) ?: ""
                        val descripcion =
                            ds.child("descripcion").getValue(String::class.java) ?: ""
                        val preferencia =
                            ds.child("preferencia").getValue(String::class.java) ?: ""
                        val genero = ds.child("genero").getValue(String::class.java) ?: ""

                        val imagen = ds.child("fotoPerfilURL").getValue(String::class.java) ?: ""
                        var intereses = ArrayList<String>()

                        for (i in ds.child("intereses").children) {
                            intereses.add(i.getValue().toString())
                        }


                        //Filtramos los usuarios que aparecen en funcion de la preferencia sexual

                        if (preferenciaUsuarioActual == "Hombres" && genero == "Hombre" && (preferencia=="Hombres" || preferencia == "No me importa") && generoUsuarioActual=="Hombre") {

                            usuarios.add(
                                Usuario(
                                    nombre,
                                    edad,
                                    descripcion,
                                    preferencia,
                                    genero,
                                    uid,
                                    imagen,
                                    intereses
                                )
                            )
                        }
                        if (preferenciaUsuarioActual == "Mujeres" && genero == "Mujer" && (preferencia=="Hombres" || preferencia == "No me importa") && generoUsuarioActual=="Hombre") {
                            usuarios.add(
                                Usuario(
                                    nombre,
                                    edad,
                                    descripcion,
                                    preferencia,
                                    genero,
                                    uid,
                                    imagen,
                                    intereses
                                )
                            )
                        }

                        if (preferenciaUsuarioActual == "Mujeres" && genero == "Mujer" && (preferencia=="Mujeres" || preferencia == "No me importa") && generoUsuarioActual=="Mujer") {

                            usuarios.add(
                                Usuario(
                                    nombre,
                                    edad,
                                    descripcion,
                                    preferencia,
                                    genero,
                                    uid,
                                    imagen,
                                    intereses
                                )
                            )
                        }

                        if (preferenciaUsuarioActual == "Hombres" && genero == "Hombre" && (preferencia=="Mujeres" || preferencia == "No me importa") && generoUsuarioActual=="Mujer") {

                            usuarios.add(
                                Usuario(
                                    nombre,
                                    edad,
                                    descripcion,
                                    preferencia,
                                    genero,
                                    uid,
                                    imagen,
                                    intereses
                                )
                            )
                        }
                    }
                }
>>>>>>> 3889ea1062d01abd39f95c64713229770d1025e4

                val gridLayout = GridLayoutManager(this@MenuPrincipal, 3)

                listadoUsuarioRecyclerView.layoutManager = gridLayout
                listadoUsuarioRecyclerView.adapter = Perfiles_adapter{
                        usuario -> usuarioSelected(usuario)
                }
                (listadoUsuarioRecyclerView.adapter as Perfiles_adapter).setData(usuarios)



                //De esta forma conseguimos accedemos a nuestro recycledView para mostrar usuarios como grid


            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("FragmentActivity", "Error: ${databaseError.message}")
            }

        }
        datos.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun usuarioSelected(usuario: Usuario){
        val intent = Intent(this, PerfilAjeno::class.java)
        intent.putExtra("nombre",usuario.nombre)
        intent.putExtra("edad",usuario.edad)
        intent.putExtra("descripcion",usuario.descripcion)
        intent.putExtra("fotoPerfil",usuario.fotoPerfil)
        intent.putExtra("genero",usuario.genero)
        intent.putExtra("preferencia",usuario.preferencia)
        intent.putExtra("intereses",usuario.intereses)
        startActivity(intent)
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