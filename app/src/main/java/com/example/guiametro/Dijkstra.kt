package com.example.guiametro

import java.util.PriorityQueue

class Dijkstra(

    private val grafo: GrafoMetro

) {

    companion object {

        private const val PENALIZACION_TRANSBORDO = 5

    }

    fun calcularRuta(

        origen: Int,

        destino: Int

    ): RutaResultado? {

        val distancias = mutableMapOf<Int, Int>()

        val anteriores = mutableMapOf<Int, Int?>()

        val transbordos = mutableMapOf<Int, Int>()

        val visitados = mutableSetOf<Int>()

        grafo.obtenerEstaciones().keys.forEach {

            distancias[it] = Int.MAX_VALUE

            anteriores[it] = null

            transbordos[it] = 0
        }

        distancias[origen] = 0

        val cola = PriorityQueue<Pair<Int, Int>>(
            compareBy { it.second }
        )

        cola.add(Pair(origen, 0))

        while (cola.isNotEmpty()) {

            val actual = cola.poll().first

            if (visitados.contains(actual))
                continue

            visitados.add(actual)

            if (actual == destino)
                break

            for (conexion in grafo.vecinos(actual)) {

                val vecino = conexion.destino.id

                var nuevoCosto =
                    distancias[actual]!! + conexion.tiempo

                var cantidadTransbordos =
                    transbordos[actual]!!

                if (conexion.esTransbordo) {

                    nuevoCosto += PENALIZACION_TRANSBORDO

                    cantidadTransbordos++

                }

                if (nuevoCosto < distancias[vecino]!!) {

                    distancias[vecino] = nuevoCosto

                    anteriores[vecino] = actual

                    transbordos[vecino] =
                        cantidadTransbordos

                    cola.add(Pair(vecino, nuevoCosto))

                }

            }

        }

        if (distancias[destino] == Int.MAX_VALUE)
            return null

        val ruta = mutableListOf<Estacion>()

        var actual: Int? = destino

        while (actual != null) {

            ruta.add(
                grafo.obtenerEstaciones()[actual]!!
            )

            actual = anteriores[actual]

        }

        ruta.reverse()

        return RutaResultado(

            estaciones = ruta,

            tiempoTotal = distancias[destino]!!,

            numeroTransbordos =
                transbordos[destino]!!

        )

    }

}