package com.example.guiametro.repository

import com.example.guiametro.*

class MetroRepository {

    val grafo = GrafoMetro()

    init {

        cargarLinea1()

    }

    private fun cargarLinea1() {

        val observatorio = Estacion(1,"Observatorio","L1")
        val tacubaya = Estacion(2,"Tacubaya","L1", esTransbordo = true)
        val juanacatlan = Estacion(3,"Juanacatlán","L1")
        val chapultepec = Estacion(4,"Chapultepec","L1")
        val sevilla = Estacion(5,"Sevilla","L1")

        grafo.agregarEstacion(observatorio)
        grafo.agregarEstacion(tacubaya)
        grafo.agregarEstacion(juanacatlan)
        grafo.agregarEstacion(chapultepec)
        grafo.agregarEstacion(sevilla)

        grafo.conectar(observatorio, tacubaya, 2)
        grafo.conectar(tacubaya, juanacatlan, 2)
        grafo.conectar(juanacatlan, chapultepec, 2)
        grafo.conectar(chapultepec, sevilla, 2)

    }

}