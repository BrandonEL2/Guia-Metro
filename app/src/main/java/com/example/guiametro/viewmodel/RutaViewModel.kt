package com.example.guiametro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guiametro.graph.Dijkstra
import com.example.guiametro.RutaResultado
import com.example.guiametro.repository.MetroRepository

class RutaViewModel : ViewModel() {

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    private val _ruta = MutableLiveData<RutaResultado?>()
    val ruta: LiveData<RutaResultado?> = _ruta

    // AQUÍ ESTÁ LA MAGIA: Traemos el repositorio y usamos su grafo ya poblado
    private val repository = MetroRepository()
    private val grafo = repository.grafo

    fun buscarRuta(origenId: Int?, destinoId: Int?) {
        if (origenId == null || destinoId == null) {
            _mensaje.value = "Selecciona un origen y destino válidos"
            return
        }

        _cargando.value = true

        try {
            // Ahora le pasamos a Dijkstra el grafo que sí tiene las 195 estaciones
            val algoritmoDijkstra = Dijkstra(grafo)
            val resultado = algoritmoDijkstra.calcularRuta(origenId, destinoId)

            if (resultado != null) {
                _ruta.value = resultado
            } else {
                _mensaje.value = "No existe una ruta disponible entre estas estaciones."
                _ruta.value = null
            }
        } catch (e: Exception) {
            _mensaje.value = "Ocurrió un error al calcular la ruta."
        } finally {
            _cargando.value = false
        }
    }

    fun limpiarBusqueda() {
        _ruta.value = null
    }
}