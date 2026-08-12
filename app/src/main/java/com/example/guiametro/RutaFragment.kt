package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guiametro.repository.MetroRepository
import com.example.guiametro.viewmodel.RutaViewModel

class RutaFragment : Fragment(R.layout.fragment_planificacion) {

    private lateinit var viewModel: RutaViewModel

    private lateinit var btnBuscar: Button
    private lateinit var progress: ProgressBar

    // Referencias exactas a los Spinners de tu XML
    private lateinit var spLineaOrigen: Spinner
    private lateinit var spEstacionOrigen: Spinner
    private lateinit var spLineaDestino: Spinner
    private lateinit var spEstacionDestino: Spinner

    private val repository = MetroRepository()
    private val estacionesPorLinea = repository.obtenerEstacionesPorLinea()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel vinculado a la Activity para que no se pierda en segundo plano
        viewModel = ViewModelProvider(requireActivity())[RutaViewModel::class.java]

        // Vincular con los IDs del XML
        btnBuscar = view.findViewById(R.id.btnBuscarRuta)
        progress = view.findViewById(R.id.progressBusqueda)

        spLineaOrigen = view.findViewById(R.id.spLineaOrigen)
        spEstacionOrigen = view.findViewById(R.id.spEstacionOrigen)
        spLineaDestino = view.findViewById(R.id.spLineaDestino)
        spEstacionDestino = view.findViewById(R.id.spEstacionDestino)

        configurarSpinnersLineas()
        observarViewModel()

        btnBuscar.setOnClickListener {
            val origenId = obtenerOrigenSeleccionado()
            val destinoId = obtenerDestinoSeleccionado()

            if (origenId != null && destinoId != null) {
                viewModel.buscarRuta(origenId, destinoId)
            } else {
                Toast.makeText(requireContext(), "Por favor selecciona origen y destino válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configurarSpinnersLineas() {
        val listaNombresLineas = listOf("Seleccione Línea") + estacionesPorLinea.keys.toList()
        val adaptadorLineas = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaNombresLineas)
        adaptadorLineas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spLineaOrigen.adapter = adaptadorLineas
        spLineaDestino.adapter = adaptadorLineas

        spLineaOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val lineaSeleccionada = parent.getItemAtPosition(position).toString()
                actualizarEstacionesParaSpinner(lineaSeleccionada, spEstacionOrigen)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spLineaDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val lineaSeleccionada = parent.getItemAtPosition(position).toString()
                actualizarEstacionesParaSpinner(lineaSeleccionada, spEstacionDestino)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun actualizarEstacionesParaSpinner(nombreLinea: String, spinnerEstacion: Spinner) {
        val estacionesDeLaLinea = estacionesPorLinea[nombreLinea] ?: emptyList()
        val nombresEstaciones = listOf("Seleccione Estación") + estacionesDeLaLinea.map { it.nombre }

        val adaptadorEstaciones = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresEstaciones)
        adaptadorEstaciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerEstacion.adapter = adaptadorEstaciones
    }

    private fun observarViewModel() {
        viewModel.cargando.observe(viewLifecycleOwner) {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.mensaje.observe(viewLifecycleOwner) { mensajeTexto ->
            Toast.makeText(requireContext(), mensajeTexto, Toast.LENGTH_SHORT).show()
        }

        viewModel.ruta.observe(viewLifecycleOwner) { resultadoRuta ->
            if (resultadoRuta != null) {
                val bundle = Bundle().apply {
                    putSerializable("rutaResultado", resultadoRuta)
                }

                findNavController().navigate(
                    R.id.action_planificacion_to_itinerario,
                    bundle
                )

                viewModel.limpiarBusqueda()
            }
        }
    }

    private fun obtenerOrigenSeleccionado(): Int? {
        val nombreSeleccionado = spEstacionOrigen.selectedItem?.toString() ?: return null
        if (nombreSeleccionado == "Seleccione Estación") return null

        val lineaActual = spLineaOrigen.selectedItem?.toString() ?: return null
        val estacionesDeLinea = estacionesPorLinea[lineaActual] ?: return null

        return estacionesDeLinea.find { it.nombre == nombreSeleccionado }?.id
    }

    private fun obtenerDestinoSeleccionado(): Int? {
        val nombreSeleccionado = spEstacionDestino.selectedItem?.toString() ?: return null
        if (nombreSeleccionado == "Seleccione Estación") return null

        val lineaActual = spLineaDestino.selectedItem?.toString() ?: return null
        val estacionesDeLinea = estacionesPorLinea[lineaActual] ?: return null

        return estacionesDeLinea.find { it.nombre == nombreSeleccionado }?.id
    }
}