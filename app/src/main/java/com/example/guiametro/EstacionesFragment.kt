package com.example.guiametro

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class EstacionesFragment : Fragment(R.layout.fragment_estaciones) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar clics para cada tarjeta usando la vista del fragmento
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