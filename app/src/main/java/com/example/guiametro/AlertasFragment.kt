package com.example.guiametro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnyRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AlertasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var alertasAdapter: AlertasAdapter
    private lateinit var db: FirebaseFirestore
    private val listaAlertas = mutableListOf<AlertaLinea>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alertas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        recyclerView = view.findViewById(R.id.recyclerAlertas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 1. Cargar lista base local para que siempre se muestren las líneas
        cargarListaBaseEstetica()

        alertasAdapter = AlertasAdapter(listaAlertas)
        recyclerView.adapter = alertasAdapter

        // 2. Sincronizar desde Firebase
        sincronizarEstadosFirebase()
    }

    private fun cargarListaBaseEstetica() {
        listaAlertas.clear()
        val lineasNombres = listOf(
            "Línea 1", "Línea 2", "Línea 3", "Línea 4",
            "Línea 5", "Línea 6", "Línea 7", "Línea 8",
            "Línea 9", "Línea 12", "Línea A", "Línea B"
        )

        for (nombre in lineasNombres) {
            listaAlertas.add(
                AlertaLinea(
                    nombre = nombre,
                    estado = "SERVICIO REGULAR",
                    estacionesAfectadas = "Ninguna",
                    fondoRes = obtenerFondoPorLinea(nombre)
                )
            )
        }
    }

    @AnyRes
    private fun obtenerFondoPorLinea(nombre: String): Int {
        return when {
            nombre.contains("12", ignoreCase = true) -> R.color.linea_12
            nombre.contains("1", ignoreCase = true) -> R.color.linea_1
            nombre.contains("2", ignoreCase = true) -> R.color.linea_2
            nombre.contains("3", ignoreCase = true) -> R.color.linea_3
            nombre.contains("4", ignoreCase = true) -> R.color.linea_4
            nombre.contains("5", ignoreCase = true) -> R.color.linea_5
            nombre.contains("6", ignoreCase = true) -> R.color.linea_6
            nombre.contains("7", ignoreCase = true) -> R.color.linea_7
            nombre.contains("8", ignoreCase = true) -> R.color.linea_8
            nombre.contains("9", ignoreCase = true) -> R.color.linea_9
            nombre.contains("A", ignoreCase = true) -> R.color.linea_a
            nombre.contains("B", ignoreCase = true) -> R.drawable.bg_tarjeta_bicolor
            else -> R.color.linea_default
        }
    }

    private fun sincronizarEstadosFirebase() {
        db.collection("estado_lineas")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombreFirebase = document.getString("nombre") ?: continue
                    val estadoFirebase = document.getString("estado") ?: "SERVICIO REGULAR"
                    val afectadasFirebase = document.getString("estacionesAfectadas") ?: "Ninguna"

                    val itemEncontrado = listaAlertas.find { it.nombre.equals(nombreFirebase, ignoreCase = true) }
                    if (itemEncontrado != null) {
                        itemEncontrado.estado = estadoFirebase
                        itemEncontrado.estacionesAfectadas = afectadasFirebase
                    }
                }

                activity?.runOnUiThread {
                    alertasAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_ERROR", "Error al sincronizar: ${e.message}")
            }
    }
}