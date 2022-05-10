package com.example.tinderus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Cambiar_contra : AppCompatActivity()  {
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambiarcontra)


        val buttonClick = findViewById<Button>(R.id.guardarcontra)
        buttonClick.setOnClickListener {

            val contra = findViewById<EditText>(R.id.nuevaContra).text.toString()
            val contra2 = findViewById<EditText>(R.id.nuevaContra2).text.toString()
            if(contra.isEmpty()){
                Toast.makeText(this, "Campo vacío, por favor introduzca la nueva contraseña ", Toast.LENGTH_SHORT).show()


            }
            else{
                if (contra == contra2 ) {
                    val user = auth.currentUser

                    user!!.updatePassword(contra).addOnSuccessListener{
                        Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()

                    }
                    val intent = Intent(this, EditarPerfil::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Las contraseñas no coinciden, intentelo de nuevo", Toast.LENGTH_SHORT).show()

                }
            }


        }







    }
}