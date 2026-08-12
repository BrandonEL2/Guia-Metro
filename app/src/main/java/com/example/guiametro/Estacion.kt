package com.example.guiametro
import java.io.Serializable

data class Estacion(

    val id: Int,

    val nombre: String,

    val linea: String,

    val salidas: List<String> = emptyList(),

    val esTransbordo: Boolean = false

): Serializable