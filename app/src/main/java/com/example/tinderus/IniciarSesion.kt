package com.example.tinderus

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

private lateinit var auth: FirebaseAuth
class IniciarSesion : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.iniciar_sesion)

        setup()


    }
    private fun setup(){
        title = "Autenticación"
        findViewById<Button>(R.id.botonInicioSesion).setOnClickListener {
            Log.i("STATE","hola")

            if(findViewById<EditText>(R.id.emailInicio).text.isNotEmpty() && findViewById<EditText>(R.id.contraInicio).text.isNotEmpty()){
                val email: String = findViewById<EditText>(R.id.emailInicio).text.toString().trim()
                val contra: String = findViewById<EditText>(R.id.contraInicio).text.toString().trim()

                Firebase.auth.signInWithEmailAndPassword(email, contra)
                    .addOnCompleteListener{

                    if (it.isSuccessful){
                        Log.i("STATE","esta weno")
                        perfil(it.result?.user?.email ?:"")
                    }else{
                        Log.i("STATE","no esta weno")
                        showAlert("Se ha producido un error autenticando al usuario")
                    }
                }
            }
            else{
                showAlert("Por favor rellene todos los campos")
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
    private fun perfil(email: String) {
        Log.d("STATE","entro aqui")
        val intent = Intent(this, ListaChats::class.java).apply {
            putExtra("email", email)

        }
        startActivity(intent)
    }


}