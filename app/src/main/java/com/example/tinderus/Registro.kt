package com.example.tinderus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/////////////////////
//    Variables    //
/////////////////////

private lateinit var auth: FirebaseAuth      //Instancia de autenticación Firebase
private lateinit var botonRegistro: Button   // Variable que hace referencia al boton de registro
private lateinit var emailRegistro: EditText // Variable que hace referencia al email de registro
private lateinit var contrasenaRegistro: EditText //Variable que hace referencia a la contraseña de registro
private lateinit var repiteContrasena: EditText //Variable que hace referencia a repetir contraseña
private lateinit var nombreRegistro: EditText   //Variable que hace referencia al nombre de registro

/////////////////////
//    Funciones    //
/////////////////////

class Registro : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Establecemos la pantalla de inicio de sesión como vista actual
        setContentView(R.layout.registro)

        //Asignamos valores a las variables
        auth = Firebase.auth
        botonRegistro = findViewById<Button>(R.id.botonRegistro)
        emailRegistro = findViewById<EditText>(R.id.emailRegistro)
        contrasenaRegistro = findViewById<EditText>(R.id.contraRegistro)
        repiteContrasena = findViewById<EditText>(R.id.repetirContraRegistro)
        nombreRegistro = findViewById<EditText>(R.id.nombreRegistro)

        //Llamamos a la función register, donde se registra al usuario
        register()
    }
    private fun register(){
        title = "Autenticación"

        //Al hacer click sobre el botón comienzan las comprobaciones sobre los datos introducidos
        botonRegistro.setOnClickListener {

            //Realizamos las comprobaciones pertinentes
            if(emailRegistro.text.isNotEmpty() &&
                contrasenaRegistro.text.isNotEmpty() &&
                contrasenaRegistro.text.toString() == repiteContrasena.text.toString() &&
                nombreRegistro.text.isNotEmpty()){

                //Usamos el método de firebase para crear un usuario mediante contraseña y email
                auth.createUserWithEmailAndPassword(
                    emailRegistro.text.toString(),
                    contrasenaRegistro.text.toString()
                ).addOnCompleteListener{

                    //Si se crea exitosamente llevamos al usuario al perfil
                    if (it.isSuccessful){
                        perfil(it.result?.user?.email ?:"", nombreRegistro.text.toString())
                    }
                    //En caso contrario se muestra un error
                    else{
                        showAlert("Se ha producido un error autenticando al usuario")
                    }
                }
            }
            //Si no se cumplen las restricciones se muestra un error
            else{
                showAlert("Por favor rellene todos los campos o ponga bien la contraseña")
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
    private fun perfil(email: String, username: String) {
        //Función auxiliar que envía al usuario autenticado hasta su lista de chats
        val intent = Intent(this, Primer_perfil::class.java).apply {
            putExtra("email", email)
            putExtra("username", username)

        }
        startActivity(intent)
    }

}