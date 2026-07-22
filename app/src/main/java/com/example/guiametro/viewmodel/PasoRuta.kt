package com.example.guiametro

data class PasoRuta(

    val titulo:String,

    val descripcion:String

)
class ItinerarioAdapter(

    private val pasos: List<PasoRuta>

)
