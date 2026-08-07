package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EstacionesFragment : Fragment(R.layout.fragment_estaciones) {

    private var searchView: SearchView? = null
    private var gridLineas: GridLayout? = null
    private var rvResultados: RecyclerView? = null
    private var adapterBusqueda: EstacionesBusquedaAdapter? = null

    // Base de datos local de estaciones
    // Base de datos de estaciones del Metro CDMX
    private val listaEstaciones = listOf(
        // Línea 1
        EstacionBusqueda("Observatorio", "1", "Observatorio - Pantitlán"),
        EstacionBusqueda("Tacubaya", "1", "Observatorio - Pantitlán", conexiones = "Línea 7, Línea 9"),
        EstacionBusqueda("Balderas", "1", "Observatorio - Pantitlán", conexiones = "Línea 3"),
        EstacionBusqueda("Salto del Agua", "1", "Observatorio - Pantitlán", conexiones = "Línea 8"),
        EstacionBusqueda("Pino Suárez", "1", "Observatorio - Pantitlán", conexiones = "Línea 2"),
        EstacionBusqueda("Candelaria", "1", "Observatorio - Pantitlán", conexiones = "Línea 4"),
        EstacionBusqueda("San Lázaro", "1", "Observatorio - Pantitlán", conexiones = "Línea B"),
        EstacionBusqueda("Pantitlán", "1", "Observatorio - Pantitlán", conexiones = "Línea 5, Línea 9, Línea A"),

        // Línea 2
        EstacionBusqueda("Cuatro Caminos", "2", "Cuatro Caminos - Tasqueña"),
        EstacionBusqueda("Tacuba", "2", "Cuatro Caminos - Tasqueña", conexiones = "Línea 7"),
        EstacionBusqueda("Hidalgo", "2", "Cuatro Caminos - Tasqueña", conexiones = "Línea 3"),
        EstacionBusqueda("Bellas Artes", "2", "Cuatro Caminos - Tasqueña", conexiones = "Línea 8"),
        EstacionBusqueda("Zócalo / Tenochtitlan", "2", "Cuatro Caminos - Tasqueña"),
        EstacionBusqueda("Chabacano", "2", "Cuatro Caminos - Tasqueña", conexiones = "Línea 8, Línea 9"),
        EstacionBusqueda("Ermita", "2", "Cuatro Caminos - Tasqueña", conexiones = "Línea 12"),
        EstacionBusqueda("Tasqueña", "2", "Cuatro Caminos - Tasqueña"),

        // Línea 3
        EstacionBusqueda("Indios Verdes", "3", "Indios Verdes - Universidad"),
        EstacionBusqueda("Deportivo 18 de Marzo", "3", "Indios Verdes - Universidad", conexiones = "Línea 6"),
        EstacionBusqueda("La Raza", "3", "Indios Verdes - Universidad", conexiones = "Línea 5"),
        EstacionBusqueda("Guerrero", "3", "Indios Verdes - Universidad", conexiones = "Línea B"),
        EstacionBusqueda("Balderas", "3", "Indios Verdes - Universidad", conexiones = "Línea 1"),
        EstacionBusqueda("Centro Médico", "3", "Indios Verdes - Universidad", conexiones = "Línea 9"),
        EstacionBusqueda("Zapata", "3", "Indios Verdes - Universidad", conexiones = "Línea 12"),
        EstacionBusqueda("Universidad", "3", "Indios Verdes - Universidad"),

        // Línea 4
        EstacionBusqueda("Martín Carrera", "4", "Santa Anita - Martín Carrera", conexiones = "Línea 6"),
        EstacionBusqueda("Consulado", "4", "Santa Anita - Martín Carrera", conexiones = "Línea 5"),
        EstacionBusqueda("Morelos", "4", "Santa Anita - Martín Carrera", conexiones = "Línea B"),
        EstacionBusqueda("Santa Anita", "4", "Santa Anita - Martín Carrera", conexiones = "Línea 8"),

        // Línea 5
        EstacionBusqueda("Politécnico", "5", "Pantitlán - Politécnico"),
        EstacionBusqueda("Instituto del Petróleo", "5", "Pantitlán - Politécnico", conexiones = "Línea 6"),
        EstacionBusqueda("Oceanía", "5", "Pantitlán - Politécnico", conexiones = "Línea B"),

        // Línea 6
        EstacionBusqueda("El Rosario", "6", "El Rosario - Martín Carrera", conexiones = "Línea 7"),

        // Línea 7
        EstacionBusqueda("Mixcoac", "7", "El Rosario - Barranca del Muerto", conexiones = "Línea 12"),
        EstacionBusqueda("Barranca del Muerto", "7", "El Rosario - Barranca del Muerto"),

        // Línea 8
        EstacionBusqueda("Garibaldi / Lagunilla", "8", "Garibaldi - Constitución de 1917", conexiones = "Línea B"),
        EstacionBusqueda("Constitución de 1917", "8", "Garibaldi - Constitución de 1917"),

        // Línea 9
        EstacionBusqueda("Jamaica", "9", "Tacubaya - Pantitlán", conexiones = "Línea 4"),

        // Línea 12
        EstacionBusqueda("Tláhuac", "12", "Mixcoac - Tláhuac"),

        // Línea A
        EstacionBusqueda("La Paz", "A", "Pantitlán - La Paz"),

        // Línea B
        EstacionBusqueda("Ciudad Azteca", "B", "Ciudad Azteca - Buenavista"),
        EstacionBusqueda("Buenavista", "B", "Ciudad Azteca - Buenavista")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inicializar vistas
        searchView = view.findViewById(R.id.searchViewEstaciones)
        gridLineas = view.findViewById(R.id.gridLineas)
        rvResultados = view.findViewById(R.id.rvResultadosBusqueda)

        // 2. Configurar RecyclerView y Adaptador de búsqueda
        rvResultados?.layoutManager = LinearLayoutManager(requireContext())
        adapterBusqueda = EstacionesBusquedaAdapter(listaEstaciones) { estacion ->
            navegarADetalle(view, "LÍNEA ${estacion.idLinea}")
        }
        rvResultados?.adapter = adapterBusqueda

        // 3. Escuchador de texto para alternar entre Cuadrícula y Resultados
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val texto = newText?.trim() ?: ""
                if (texto.isNotEmpty()) {
                    gridLineas?.visibility = View.GONE
                    rvResultados?.visibility = View.VISIBLE
                    adapterBusqueda?.filtrar(texto)
                } else {
                    gridLineas?.visibility = View.VISIBLE
                    rvResultados?.visibility = View.GONE
                }
                return true
            }
        })

        // 4. Configurar clics para cada tarjeta usando la vista del fragmento
        view.findViewById<CardView>(R.id.cardLinea1)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 1")
        }
        view.findViewById<CardView>(R.id.cardLinea2)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 2")
        }
        view.findViewById<CardView>(R.id.cardLinea3)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 3")
        }
        view.findViewById<CardView>(R.id.cardLinea4)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 4")
        }
        view.findViewById<CardView>(R.id.cardLinea5)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 5")
        }
        view.findViewById<CardView>(R.id.cardLinea6)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 6")
        }
        view.findViewById<CardView>(R.id.cardLinea7)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 7")
        }
        view.findViewById<CardView>(R.id.cardLinea8)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 8")
        }
        view.findViewById<CardView>(R.id.cardLinea9)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 9")
        }
        view.findViewById<CardView>(R.id.cardLinea12)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA 12")
        }
        view.findViewById<CardView>(R.id.cardLineaA)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA A")
        }
        view.findViewById<CardView>(R.id.cardLineaB)?.setOnClickListener {
            navegarADetalle(view, "LÍNEA B")
        }
    }

    // Función auxiliar declarada correctamente dentro de la clase
    private fun navegarADetalle(view: View, nombreLinea: String) {
        val bundle = Bundle().apply {
            putString("linea_seleccionada", nombreLinea)
        }
        view.findNavController().navigate(R.id.detalleLineaFragment, bundle)
    }
}