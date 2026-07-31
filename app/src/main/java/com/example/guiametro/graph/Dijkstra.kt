package com.example.guiametro.graph

import com.example.guiametro.*

class Dijkstra(
    private val grafo: GrafoMetro
) {

    companion object {
        private const val PENALIZACION_TRANSBORDO = 5
    }

    fun calcularRuta(
        origen: Estacion,
        destino: Estacion
    ): RutaResultado? {

        // Obtenemos el mapa de estaciones (ID -> Estacion)
        val mapaEstaciones = grafo.obtenerEstaciones()

        // Validar que ambas estaciones existan en el grafo
        if (!mapaEstaciones.containsValue(origen) || !mapaEstaciones.containsValue(destino)) {
            return null
        }

        val distancias = mutableMapOf<Estacion, Int>()
        val anteriores = mutableMapOf<Estacion, Estacion?>()
        val visitados = mutableSetOf<Estacion>()

        // Inicializamos las distancias para cada estación del mapa
        for (estacion in mapaEstaciones.values) {
            distancias[estacion] = Int.MAX_VALUE
            anteriores[estacion] = null
        }

        distancias[origen] = 0

        while (visitados.size < mapaEstaciones.size) {

            val actual = distancias
                .filter { !visitados.contains(it.key) }
                .minByOrNull { it.value }
                ?.key ?: break

            if (actual == destino)
                break

            visitados.add(actual)

            // Usamos el método 'vecinos' de tu GrafoMetro pasándole el ID de la estación
            val conexiones = grafo.vecinos(actual.id)

            for (conexion in conexiones) {

                var peso = conexion.tiempo

            if (conexion.esTransbordo)
                    peso += PENALIZACION_TRANSBORDO

                val nuevaDistancia = distancias[actual]!! + peso

                if (nuevaDistancia < (distancias[conexion.destino] ?: Int.MAX_VALUE)) {

                    distancias[conexion.destino] = nuevaDistancia
                    anteriores[conexion.destino] = actual

                }

            }

        }

        if (distancias[destino] == null || distancias[destino] == Int.MAX_VALUE)
            return null

        val ruta = mutableListOf<Estacion>()
        var actual: Estacion? = destino

        while (actual != null) {
            ruta.add(0, actual)
            actual = anteriores[actual]
        }

        val transbordos = ruta.zipWithNext().count {
            it.first.linea != it.second.linea
        }

        return RutaResultado(
            estaciones = ruta,
            tiempoTotal = distancias[destino]!!,
            numeroTransbordos = transbordos
        )
    }
}