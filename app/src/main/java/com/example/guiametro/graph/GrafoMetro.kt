package com.example.guiametro.graph

import com.example.guiametro.Conexion
import com.example.guiametro.Estacion

class GrafoMetro {

    private val estaciones = mutableMapOf<Int, Estacion>()

    private val conexiones = mutableMapOf<Int, MutableList<Conexion>>()

    fun agregarEstacion(estacion: Estacion) {

        estaciones[estacion.id] = estacion
        conexiones[estacion.id] = mutableListOf()

    }

    fun conectar(
        origen: Int,
        destino: Int,
        tiempo: Int,
        transbordo: Boolean = false
    ) {

        val estOrigen = estaciones[origen] ?: return
        val estDestino = estaciones[destino] ?: return

        conexiones[origen]?.add(
            Conexion(estDestino, tiempo, transbordo)
        )

        conexiones[destino]?.add(
            Conexion(estOrigen, tiempo, transbordo)
        )
    }

    fun vecinos(id: Int): List<Conexion> {

        return conexiones[id] ?: emptyList()

    }

    fun obtenerEstaciones(): Map<Int, Estacion> {

        return estaciones

    }

}