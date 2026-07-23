package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.adapter.ItinerarioAdapter

class ItinerarioFragment : Fragment(R.layout.fragment_itinerario) {

    private lateinit var txtTiempo: TextView
    private lateinit var txtTransbordos: TextView
    private lateinit var txtLlegada: TextView
    private lateinit var rvItinerario: RecyclerView
    private lateinit var btnNuevaRuta: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Vincular vistas con los IDs exactos de tu XML
        txtTiempo = view.findViewById(R.id.txtTiempo)
        txtTransbordos = view.findViewById(R.id.txtTransbordos)
        txtLlegada = view.findViewById(R.id.txtLlegada)
        rvItinerario = view.findViewById(R.id.rvItinerario)
        btnNuevaRuta = view.findViewById(R.id.btnNuevaRuta)

        // 2. Recuperar el objeto RutaResultado enviado desde el RutaFragment
        val resultadoRuta = arguments?.getSerializable("rutaResultado") as? RutaResultado

        // 3. Pintar los datos en los TextViews si existen
        if (resultadoRuta != null) {
            txtTiempo.text = "Tiempo estimado: ${resultadoRuta.tiempoTotal} min"
            txtTransbordos.text = "Transbordos: ${resultadoRuta.numeroTransbordos}"

            // Configurar el RecyclerView para mostrar las estaciones paso a paso
            rvItinerario.layoutManager = LinearLayoutManager(requireContext())
            // Asegúrate de pasar la lista de estaciones a tu adaptador existente (ItinerarioAdapter)
            rvItinerario.adapter = ItinerarioAdapter(resultadoRuta.estaciones)
        }

        // 4. Configurar el botón de "Nueva búsqueda" para volver a la pantalla de planificación
        btnNuevaRuta.setOnClickListener {
            findNavController().popBackStack(R.id.rutaFragment, false)
        }
    }
}