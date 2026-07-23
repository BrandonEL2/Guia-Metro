package com.example.guiametro
import java.io.Serializable
data class RutaResultado(
    val estaciones: List<Estacion>,
    val tiempoTotal: Int,
    val numeroTransbordos: Int
) : Serializable