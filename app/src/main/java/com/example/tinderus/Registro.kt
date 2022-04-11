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

private lateinit var auth: FirebaseAuth   //Instancia de autenticación Firebase

/////////////////////
//    Funciones    //
/////////////////////

class Registro : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Establecemos la pantalla de inicio de sesión como vista actual
        setContentView(R.layout.registro)

        //

        //Llamamos a la función register, donde se registra al usuario
        register()
    }
    private fun register(){
        title = "Autenticación"
        findViewById<Button>(R.id.botonRegistro).setOnClickListener {

            if(findViewById<EditText>(R.id.emailRegistro).text.isNotEmpty() && findViewById<EditText>(R.id.contraRegistro).text.isNotEmpty() && findViewById<EditText>(R.id.contraRegistro).text.toString()== findViewById<EditText>(R.id.repetirContraRegistro).text.toString() && findViewById<EditText>(R.id.nombreRegistro).text.isNotEmpty()){

                Firebase.auth.createUserWithEmailAndPassword(findViewById<EditText>(R.id.emailRegistro).text.toString(),
                    findViewById<EditText>(R.id.contraRegistro).text.toString()
                ).addOnCompleteListener{

                    if (it.isSuccessful){
                        println("registro esho")
                        perfil(it.result?.user?.email ?:"", findViewById<EditText>(R.id.nombreRegistro).text.toString())
                    }else{
                        showAlert("Se ha producido un error autenticando al usuario")
                    }
                }
            }
            else{
                showAlert("Por favor rellene todos los campos o ponga bien la contraseña")
            }
        }
    }

    private fun showAlert(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun perfil(email: String, username: String) {
        val intent = Intent(this, Primer_perfil::class.java).apply {
            putExtra("email", email)
            putExtra("username", username)

        }
        startActivity(intent)
    }

}