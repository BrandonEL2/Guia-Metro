package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
class EstacionesFragment : Fragment(R.layout.fragment_estaciones) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar clics para interactuar con cada tarjeta de línea
        view.findViewById<CardView>(R.id.cardLinea1).setOnClickListener {
            Toast.makeText(requireContext(), "Mostrando estaciones de Línea 1", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<CardView>(R.id.cardLinea3).setOnClickListener {
            Toast.makeText(requireContext(), "Mostrando estaciones de Línea 3", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<CardView>(R.id.cardLinea1).setOnClickListener { v ->
            val bundle = Bundle().apply { putString("linea_seleccionada", "LÍNEA 1") }
            v.findNavController().navigate(R.id.detalleLineaFragment, bundle)
        }
    }
}