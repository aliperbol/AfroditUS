package com.example.tinderus

class Chat {
    var id: String = ""
    var nombre: String = ""
    var usuarios: List<String> = emptyList()

    constructor(id:String, nombre:String, usuarios:List<String>){
        this.id = id
        this.nombre = nombre
        this.usuarios = usuarios
    }
}