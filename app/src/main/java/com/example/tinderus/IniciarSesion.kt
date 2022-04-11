package com.example.tinderus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/////////////////////
//    Variables    //
/////////////////////

private lateinit var auth: FirebaseAuth          //Instancia de autenticación Firebase
private lateinit var botonInicioSesion: Button   //Variable que almacena la vista del boton Inicio sesion
private lateinit var emailInicioSesion: EditText //Variable que almacena el email de inicio de sesión
private lateinit var contrasenaInicioSesion: EditText //Variable que almacena la contraseña de inicio de sesión

/////////////////////
//    Funciones    //
/////////////////////

class IniciarSesion : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Establecemos la pantalla de inicio de sesión como vista actual
        setContentView(R.layout.iniciar_sesion)

        //Asignación de valores a variables
        botonInicioSesion = findViewById<Button>(R.id.botonInicioSesion)
        emailInicioSesion = findViewById<EditText>(R.id.emailInicio)
        contrasenaInicioSesion = findViewById<EditText>(R.id.contraInicio)

        //Llamamos a la función login, donde se autentica al usuario
        login()
    }

    private fun login(){
        //Función destinada a la autenticación del usuario
        title = "Autenticación"

        //Al hacer click sobre el boton iniciar sesión comienzan las comprobaciones
        botonInicioSesion.setOnClickListener {

            //Comprobamos que el email y la contraseña no estén vacíos
            if(emailInicioSesion.text.isNotEmpty() && contrasenaInicioSesion.text.isNotEmpty()){

                //En caso de no estarlo, creamos dos variables auxiliares a partir del email y contraseña
                //tomados anteriormente y les aplicamos un trim para eliminar espacios
                val emailAuth: String = emailInicioSesion.text.toString().trim()
                val contrasenaAuth: String = contrasenaInicioSesion.text.toString().trim()

                //Ejecutamos la función de autenticación mediante la instancia de Firebase
                Firebase.auth.signInWithEmailAndPassword(emailAuth, contrasenaAuth)
                    .addOnCompleteListener{

                    //En caso de inicio exitoso, ejecutamos la función chats, que enviará al usuario
                    //hasta su lista de chats
                    if (it.isSuccessful){
                        chats()
                    }
                    //En caso contrario, mostramos un mensaje de error
                    else{
                        showAlert("Se ha producido un error autenticando al usuario")
                    }
                }
            }
            //En caso de estar vacíos, indicamos al usuario que debe rellenar los campos
            else{
                showAlert("Por favor rellene todos los campos")
            }
        }
    }

    ////////////////////////////////
    //    Funciones Auxiliares    //
    ////////////////////////////////

    private fun showAlert(mensaje: String){
        //Funcion auxiliar para mostrar mensajes
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun chats() {
        //Función auxiliar que envía al usuario autenticado hasta su lista de chats
        val intent = Intent(this, Primer_perfil::class.java) //Cambiar a chats
        startActivity(intent)
    }
}

