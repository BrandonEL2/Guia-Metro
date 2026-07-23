package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.adapter.DetalleLineaAdapter

class DetalleLineaFragment : Fragment(R.layout.fragment_detalle_linea) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titulo = view.findViewById<TextView>(R.id.txtTituloLinea)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvEstacionesLinea)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Recogemos el argumento enviado al hacer clic (ej. "Línea 1")
        val nombreLinea = arguments?.getString("linea_seleccionada") ?: "Línea 1"
        titulo.text = "ESTACIONES DE $nombreLinea"

        // Lista de ejemplo de estaciones para esa línea (puedes filtrarlas de tu base de datos)
        val estacionesDePrueba = listOf(
            Estacion(1, "Observatorio", nombreLinea, listOf("L12"), true),
            Estacion(2, "Tacubaya", nombreLinea, listOf("L7", "L9", "L2"), true),
            Estacion(3, "Juanacatlán", nombreLinea, emptyList(), false),
            Estacion(4, "Chapultepec", nombreLinea, emptyList(), false),
            Estacion(5, "Sevilla", nombreLinea, emptyList(), false),
            Estacion(6, "Insurgentes", nombreLinea, listOf("L1"), true),
            Estacion(7, "Cuauhtémoc", nombreLinea, listOf("L3"), true),
            Estacion(8, "Balderas", nombreLinea, listOf("L3", "L8"), true),
            Estacion(9, "Pantitlán", nombreLinea, listOf("L5", "L9", "LA"), true)
        )

        recyclerView.adapter = DetalleLineaAdapter(estacionesDePrueba)
    }
}