package com.example.guiametro

class GrafoMetro {

    private val estaciones = mutableMapOf<Int, Estacion>()

    private val conexiones =
        mutableMapOf<Int, MutableList<Conexion>>()

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

        val origenNodo = estaciones[origen] ?: return

        val destinoNodo = estaciones[destino] ?: return

        conexiones[origen]?.add(

            Conexion(

                destinoNodo,

                tiempo,

                transbordo

            )
        )

        conexiones[destino]?.add(

            Conexion(

                origenNodo,

                tiempo,

                transbordo

            )
        )
    }

    fun obtenerEstaciones(): Map<Int, Estacion> {

        return estaciones

    }

    fun vecinos(id: Int): List<Conexion> {

        return conexiones[id] ?: emptyList()

    }

}