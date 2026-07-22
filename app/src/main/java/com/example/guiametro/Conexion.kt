package com.example.guiametro

data class Conexion(

    val destino: Estacion,

    val tiempo: Int,

    val transbordo: Boolean = false

)