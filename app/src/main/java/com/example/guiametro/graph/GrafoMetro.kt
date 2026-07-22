package com.example.guiametro

class GrafoMetro {

    private val estaciones = mutableListOf<Estacion>()

    private val conexiones =
        mutableMapOf<Estacion, MutableList<Conexion>>()

    fun agregarEstacion(estacion: Estacion) {

        estaciones.add(estacion)

        conexiones[estacion] = mutableListOf()

    }

    fun conectar(
        origen: Estacion,
        destino: Estacion,
        tiempo: Int,
        transbordo: Boolean = false
    ) {

        conexiones[origen]?.add(
            Conexion(destino, tiempo, transbordo)
        )

        conexiones[destino]?.add(
            Conexion(origen, tiempo, transbordo)
        )

    }

    fun obtenerConexiones(estacion: Estacion): List<Conexion> {

        return conexiones[estacion] ?: emptyList()

    }

    fun obtenerEstaciones(): List<Estacion> {

        return estaciones

    }

}