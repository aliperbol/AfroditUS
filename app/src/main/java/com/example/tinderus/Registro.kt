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

private lateinit var auth: FirebaseAuth

class Registro : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        val buttonClick = findViewById<Button>(R.id.botonRegistro)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Primer_perfil::class.java)
            startActivity(intent)
        }

        setup()
    }
    private fun setup(){
        title = "Autenticación"
        findViewById<Button>(R.id.botonRegistro).setOnClickListener {
            if(findViewById<EditText>(R.id.emailRegistro).text.isNotEmpty() && findViewById<EditText>(R.id.contraRegistro).text.isNotEmpty()){

                Firebase.auth.createUserWithEmailAndPassword(findViewById<EditText>(R.id.emailRegistro).text.toString(),
                    findViewById<EditText>(R.id.contraRegistro).text.toString()
                ).addOnCompleteListener{

                    if (it.isSuccessful){
                        perfil(it.result?.user?.email ?:"", ProviderType.BASIC)
                    }else{
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
        val intent = Intent(this, Primer_perfil::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(intent)
    }

}