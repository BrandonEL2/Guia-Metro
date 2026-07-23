package com.example.guiametro.repository

import com.example.guiametro.*
import com.example.guiametro.graph.GrafoMetro

class MetroRepository {

    val grafo = GrafoMetro()

    init {
        cargarLineas()
    }

    private fun cargarLineas() {
        val observatorio = Estacion(1, "Observatorio", "L1")
        val tacubaya = Estacion(2, "Tacubaya", "L1", esTransbordo = true)
        val juanacatlan = Estacion(3, "Juanacatlán", "L1")
        val chapultepec = Estacion(4, "Chapultepec", "L1")
        val sevilla = Estacion(5, "Sevilla", "L1")

        grafo.agregarEstacion(observatorio)
        grafo.agregarEstacion(tacubaya)
        grafo.agregarEstacion(juanacatlan)
        grafo.agregarEstacion(chapultepec)
        grafo.agregarEstacion(sevilla)

        grafo.conectar(observatorio.id, tacubaya.id, 2)
        grafo.conectar(tacubaya.id, juanacatlan.id, 2)
        grafo.conectar(juanacatlan.id, chapultepec.id, 2)
        grafo.conectar(chapultepec.id, sevilla.id, 2)
    }

    // Retorna un mapa que asocia el nombre de la línea con su lista de estaciones
    fun obtenerEstacionesPorLinea(): Map<String, List<Estacion>> {
        return mapOf(
            "Línea 1" to listOf(
                Estacion(1, "Observatorio", "L1"),
                Estacion(2, "Tacubaya", "L1", esTransbordo = true),
                Estacion(3, "Juanacatlán", "L1"),
                Estacion(4, "Chapultepec", "L1"),
                Estacion(5, "Sevilla", "L1")
            )
            // Aquí puedes agregar más líneas conforme las vayas registrando en tu grafo
        )
    }
}