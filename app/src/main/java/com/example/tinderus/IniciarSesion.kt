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

                Firebase.auth.signInWithEmailAndPassword(findViewById<EditText>(R.id.emailRegistro).text.toString(),
                    findViewById<EditText>(R.id.contraRegistro).text.toString()
                ).addOnCompleteListener{

                    if (it.isSuccessful){
                        Log.i("STATE","esta weno")
                        perfil(it.result?.user?.email ?:"", ProviderType.BASIC)
                    }else{
                        Log.i("STATE","no esta weno")
                        showAlert()
                    }
                }
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun perfil(email: String, provider: ProviderType) {
        Log.d("STATE","entro aqui")
        val intent = Intent(this, MenuPrincipal::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(intent)
    }


}