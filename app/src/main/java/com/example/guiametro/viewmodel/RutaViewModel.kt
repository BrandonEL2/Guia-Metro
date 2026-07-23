package com.example.guiametro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guiametro.Estacion
import com.example.guiametro.RutaResultado
import com.example.guiametro.graph.Dijkstra
import com.example.guiametro.graph.GrafoMetro

class RutaViewModel : ViewModel() {

    private val grafo = GrafoMetro()
    private val dijkstra = Dijkstra(grafo)

    private val _ruta = MutableLiveData<RutaResultado?>()
    val ruta: LiveData<RutaResultado?> get() = _ruta

    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> get() = _cargando

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    init {
        inicializarMetro()
    }

    private fun inicializarMetro() {
        // Cargar estaciones de la Línea 1 directamente aquí para que el grafo las reconozca
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

        // Conectar estaciones (el método .conectar de tu GrafoMetro ya maneja bidireccionalidad)
        grafo.conectar(observatorio.id, tacubaya.id, 2)
        grafo.conectar(tacubaya.id, juanacatlan.id, 2)
        grafo.conectar(juanacatlan.id, chapultepec.id, 2)
        grafo.conectar(chapultepec.id, sevilla.id, 2)
    }

    fun buscarRuta(origen: Int?, destino: Int?) {
        if (origen == null || destino == null) {
            _mensaje.value = "Seleccione una estación de origen y una de destino."
            return
        }

        if (origen == destino) {
            _mensaje.value = "El origen y el destino no pueden ser iguales."
            return
        }

        _cargando.value = true
        val resultado = dijkstra.calcularRuta(origen, destino)
        _cargando.value = false

        if (resultado == null) {
            _mensaje.value = "No existe una ruta disponible entre estas estaciones."
            _ruta.value = null
        } else {
            _ruta.value = resultado
        }
    }

    fun limpiarBusqueda() {
        _ruta.value = null
    }
}