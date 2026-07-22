package com.example.guiametro

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.adapter.ItinerarioAdapter
import java.time.LocalTime

class ItinerarioFragment : Fragment(R.layout.fragment_itinerario) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ruta: RutaResultado? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("ruta", RutaResultado::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable("ruta")
            }

        ruta?.let {

            view.findViewById<TextView>(R.id.txtTiempo).text =
                "Tiempo estimado: ${it.tiempoTotal} min"

            view.findViewById<TextView>(R.id.txtTransbordos).text =
                "Transbordos: ${it.numeroTransbordos}"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val llegada = LocalTime.now().plusMinutes(it.tiempoTotal.toLong())

                view.findViewById<TextView>(R.id.txtLlegada).text =
                    "Llegada: $llegada"
            } else {
                view.findViewById<TextView>(R.id.txtLlegada).text =
                    "Hora de llegada no disponible"
            }

            val recycler = view.findViewById<RecyclerView>(R.id.rvItinerario)

            recycler.layoutManager = LinearLayoutManager(requireContext())

            recycler.adapter = ItinerarioAdapter(it.estaciones)
        }
    }
}