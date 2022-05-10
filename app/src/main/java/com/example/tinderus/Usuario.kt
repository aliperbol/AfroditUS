package com.example.tinderus

import java.util.*
import kotlin.collections.ArrayList

data class Usuario (
    var nombre: String = "",
    var edad: String = "",
    var descripcion: String = "",
    var preferencia: String = "",
    var genero: String = "",
    var uid: String ="",
    var fotoPerfil: String ="",
    var intereses: ArrayList<String> = ArrayList()
)