package com.example.guiametro


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guiametro.model.Dijkstra
import com.metroguia.model.GrafoMetro
import com.metroguia.model.RutaResultado

class RutaViewModel : ViewModel() {

    /**
     * Grafo del Metro.
     * En la siguiente fase podrá cargarse desde JSON local o Room.
     */
    private val grafo = GrafoMetro()

    /**
     * Algoritmo de búsqueda.
     */
    private val dijkstra = Dijkstra(grafo)

    /**
     * Resultado encontrado.
     */
    private val _ruta = MutableLiveData<RutaResultado?>()
    val ruta: LiveData<RutaResultado?>
        get() = _ruta

    /**
     * Estado de carga.
     */
    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean>
        get() = _cargando

    /**
     * Mensajes para la interfaz.
     */
    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String>
        get() = _mensaje

    init {
        inicializarMetro()
    }

    /**
     * Carga inicial del grafo.
     *
     * Más adelante este método leerá un JSON
     * o una base Room con todas las estaciones.
     */
    private fun inicializarMetro() {

        // TODO:
        // Agregar todas las estaciones y conexiones reales.
        //
        // Ejemplo:
        //
        // grafo.agregarEstacion(...)
        // grafo.conectar(...)
    }

    /**
     * Busca la mejor ruta.
     */
    fun buscarRuta(origen: Int?, destino: Int?) {

        // RN-01
        if (origen == null || destino == null) {

            _mensaje.value =
                "Seleccione una estación de origen y una de destino."

            return
        }

        if (origen == destino) {

            _mensaje.value =
                "El origen y el destino no pueden ser iguales."

            return
        }

        _cargando.value = true

        val resultado =
            dijkstra.calcularRuta(origen, destino)

        _cargando.value = false

        if (resultado == null) {

            _mensaje.value =
                "No existe una ruta disponible."

            _ruta.value = null

        } else {

            _ruta.value = resultado

        }

    }

    /**
     * Limpia el resultado actual.
     */
    fun limpiarBusqueda() {

        _ruta.value = null

    }

}