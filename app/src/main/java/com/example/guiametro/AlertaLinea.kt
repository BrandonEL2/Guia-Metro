package com.example.guiametro

import androidx.annotation.AnyRes

data class AlertaLinea(
    val nombre: String,
    var estado: String,
    var estacionesAfectadas: String,
    @AnyRes var fondoRes: Int
)