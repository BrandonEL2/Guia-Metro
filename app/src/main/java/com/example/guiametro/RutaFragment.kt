package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.guiametro.viewmodel.RutaViewModel
import androidx.navigation.fragment.findNavController
import android.widget.ArrayAdapter
import android.widget.Spinner

private lateinit var spLineaOrigen: Spinner
private lateinit var spEstacionOrigen: Spinner

private lateinit var spLineaDestino: Spinner
private lateinit var spEstacionDestino: Spinner

data class RutaResultados(

    val estaciones: List<Estacion>,

    val tiempoTotal: Int,

    val numeroTransbordos: Int

)

class RutaFragment : Fragment(R.layout.fragment_planificacion) {

    private lateinit var viewModel: RutaViewModel

    private lateinit var btnBuscar: Button
    private lateinit var progress: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this)[RutaViewModel::class.java]

        btnBuscar = view.findViewById(R.id.btnBuscarRuta)
        progress = view.findViewById(R.id.progressBusqueda)

        observarViewModel()

        btnBuscar.setOnClickListener {

            val origen = obtenerOrigenSeleccionado()

            val destino = obtenerDestinoSeleccionado()

            viewModel.buscarRuta(origen, destino)

        }

    }

    private fun observarViewModel() {

        viewModel.cargando.observe(viewLifecycleOwner) {

            progress.visibility =
                if (it) View.VISIBLE else View.GONE

        }

        viewModel.mensaje.observe(viewLifecycleOwner) {

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        }

        viewModel.ruta.observe(viewLifecycleOwner) {

            if (it != null) {

                val bundle = Bundle()

                findNavController().navigate(
                    R.id.action_planificacion_to_itinerario,
                    bundle
                )

            }

        }

    }

    private fun obtenerOrigenSeleccionado(): Int? {
        // Implementar lectura del Spinner
        return null
    }

    private fun obtenerDestinoSeleccionado(): Int? {
        // Implementar lectura del Spinner
        return null
    }
}