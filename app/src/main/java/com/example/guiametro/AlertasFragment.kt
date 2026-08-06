package com.example.guiametro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnyRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

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

        // 1. Carga inicial de elementos
        cargarListaBaseEstetica()

        alertasAdapter = AlertasAdapter(listaAlertas)
        recyclerView.adapter = alertasAdapter

        // 2. Botón para forzar actualización manual
        val btnActualizar = view.findViewById<View>(R.id.btnActualizar)
        btnActualizar?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                consultarEstadoOficialMetro()
            }
        }

        // 3. Iniciar temporizador en tiempo real (Refresco cada 30 segundos)
        iniciarBucleTiempoReal()
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

    private fun iniciarBucleTiempoReal() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            while (isActive) {
                consultarEstadoOficialMetro()
                delay(30000) // Espera 30 segundos antes de la siguiente consulta
            }
        }
    }

    private suspend fun consultarEstadoOficialMetro() {
        try {
            val doc = Jsoup.connect("https://www.metro.cdmx.gob.mx/la-red/estado-del-servicio")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(10000)
                .get()

            // Seleccionamos las filas de la tabla oficial
            val filasTabla = doc.select("table tr")

            for (tr in filasTabla) {
                val celdas = tr.select("td")
                if (celdas.size >= 2) {
                    val htmlCeldaIcono = celdas[0].outerHtml()
                    val textoEstadoReal = celdas[1].text().trim()

                    if (textoEstadoReal.isNotEmpty()) {
                        for (alerta in listaAlertas) {
                            val idLinea = alerta.nombre.replace(Regex("(?i)línea|linea"), "").trim()

                            // Compara si la primera celda contiene el número/letra de la línea en su texto, imagen o atributos
                            val esCoincidencia = celdas[0].text().contains(idLinea, ignoreCase = true) ||
                                    htmlCeldaIcono.contains("linea_$idLinea", ignoreCase = true) ||
                                    htmlCeldaIcono.contains("linea$idLinea", ignoreCase = true) ||
                                    htmlCeldaIcono.contains("linea-$idLinea", ignoreCase = true) ||
                                    htmlCeldaIcono.contains("l$idLinea", ignoreCase = true)

                            if (esCoincidencia) {
                                // Asigna directamente el estado real reportado por el sitio web
                                alerta.estado = textoEstadoReal.uppercase()
                                break
                            }
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                alertasAdapter.notifyDataSetChanged()
            }

        } catch (e: Exception) {
            Log.e("SCRAPING_ERROR", "Fallo al consultar web oficial: ${e.message}")
            sincronizarEstadosFirebase()
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

                    val itemEncontrado = listaAlertas.find { item ->
                        val localLimpio = item.nombre.replace("í", "i").replace("Í", "I")
                        val fbLimpio = nombreFirebase.replace("í", "i").replace("Í", "I")
                        localLimpio.equals(fbLimpio, ignoreCase = true)
                    }

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

    @AnyRes
    private fun obtenerFondoPorLinea(nombre: String): Int {
        val idLinea = nombre.replace(Regex("(?i)línea|linea"), "").trim()

        return when {
            idLinea.equals("12", ignoreCase = true) -> R.color.linea_12
            idLinea.equals("1", ignoreCase = true) -> R.color.linea_1
            idLinea.equals("2", ignoreCase = true) -> R.color.linea_2
            idLinea.equals("3", ignoreCase = true) -> R.color.linea_3
            idLinea.equals("4", ignoreCase = true) -> R.color.linea_4
            idLinea.equals("5", ignoreCase = true) -> R.color.linea_5
            idLinea.equals("6", ignoreCase = true) -> R.color.linea_6
            idLinea.equals("7", ignoreCase = true) -> R.color.linea_7
            idLinea.equals("8", ignoreCase = true) -> R.color.linea_8
            idLinea.equals("9", ignoreCase = true) -> R.color.linea_9
            idLinea.equals("A", ignoreCase = true) -> R.color.linea_a
            idLinea.equals("B", ignoreCase = true) -> R.drawable.bg_tarjeta_bicolor
            else -> R.color.linea_default
        }
    }
}