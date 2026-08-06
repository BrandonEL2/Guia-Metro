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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            Log.d("SCRAPING_DEBUG", "Iniciando escaneo de la página principal e iframes...")

            val urlPrincipal = "https://www.metro.cdmx.gob.mx/la-red/estado-del-servicio"
            val documentosCargados = mutableListOf<org.jsoup.nodes.Document>()
            val urlsVisitadas = mutableSetOf<String>()

            fun cargarDocumentoEIframes(url: String) {
                if (urlsVisitadas.contains(url)) return
                urlsVisitadas.add(url)

                try {
                    val doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                        .timeout(15000)
                        .get()

                    documentosCargados.add(doc)

                    val iframes = doc.select("iframe")
                    for (iframe in iframes) {
                        var src = iframe.attr("src")
                        if (src.isNotEmpty()) {
                            if (!src.startsWith("http")) {
                                src = if (src.startsWith("/")) "https://www.metro.cdmx.gob.mx$src" else "https://www.metro.cdmx.gob.mx/$src"
                            }
                            cargarDocumentoEIframes(src)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SCRAPING_DEBUG", "Error cargando URL ($url): ${e.message}")
                }
            }

            cargarDocumentoEIframes(urlPrincipal)

            val todasLasFilas = documentosCargados.flatMap { it.select("tr") }
            Log.d("SCRAPING_DEBUG", "Documentos procesados: ${documentosCargados.size} | Filas totales: ${todasLasFilas.size}")

            var huboCambios = false
            val lineasFaltantes = mutableListOf<AlertaLinea>()

            for (alerta in listaAlertas) {
                val idLinea = alerta.nombre.replace(Regex("(?i)línea|linea"), "").trim().lowercase()
                var encontrada = false

                for (fila in todasLasFilas) {
                    if (fila.select("th").isNotEmpty()) continue

                    val celdas = fila.select("td")
                    if (celdas.size >= 2) {
                        val celda0 = celdas[0]
                        val htmlCelda0 = celda0.outerHtml().lowercase()
                        val textoCelda0 = celda0.text().trim().lowercase()

                        val estadoTexto = celdas[1].text().trim()
                        val afectadasTexto = if (celdas.size >= 3) celdas[2].text().trim() else "Ninguna"

                        if (estadoTexto.isEmpty() || estadoTexto.contains("estado", ignoreCase = true)) continue

                        val coincide = when (idLinea) {
                            "a" -> textoCelda0 == "a" || htmlCelda0.contains("/la.") || htmlCelda0.contains("linea_a")
                            "b" -> textoCelda0 == "b" || htmlCelda0.contains("/lb.") || htmlCelda0.contains("linea_b")
                            "12" -> textoCelda0.contains("12") || htmlCelda0.contains("12")
                            else -> {
                                val regexNum = Regex("(^|\\D)$idLinea(\\D|$)")
                                regexNum.containsMatchIn(textoCelda0) || regexNum.containsMatchIn(htmlCelda0)
                            }
                        }

                        if (coincide) {
                            alerta.estado = estadoTexto.uppercase()
                            alerta.estacionesAfectadas = if (afectadasTexto.isEmpty()) "Ninguna" else afectadasTexto
                            huboCambios = true
                            encontrada = true
                            Log.d("SCRAPING_DEBUG", "Éxito -> ${alerta.nombre}: ${alerta.estado} | Afectadas: ${alerta.estacionesAfectadas}")
                            break
                        }
                    }
                }

                if (!encontrada) {
                    lineasFaltantes.add(alerta)
                    Log.w("SCRAPING_DEBUG", "No presente en HTML: ${alerta.nombre}. Recurriendo a respaldo.")
                }
            }

            if (lineasFaltantes.isNotEmpty()) {
                sincronizarLineasFaltantesFirebase(lineasFaltantes)
            }

            withContext(Dispatchers.Main) {
                if (huboCambios) {
                    alertasAdapter.notifyDataSetChanged()
                }
            }

        } catch (e: Exception) {
            Log.e("SCRAPING_ERROR", "Error de lectura: ${e.message}", e)
            withContext(Dispatchers.Main) {
                sincronizarEstadosFirebase()
            }
        }
    }

    private fun sincronizarLineasFaltantesFirebase(faltantes: List<AlertaLinea>) {
        val database = FirebaseDatabase.getInstance().getReference("lineas")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (alerta in faltantes) {
                    val key = alerta.nombre.replace(" ", "_").lowercase()
                    val lineaSnapshot = snapshot.child(key)
                    if (lineaSnapshot.exists()) {
                        alerta.estado = lineaSnapshot.child("estado").getValue(String::class.java) ?: "SERVICIO REGULAR"
                        alerta.estacionesAfectadas = lineaSnapshot.child("estacionesAfectadas").getValue(String::class.java) ?: "Ninguna"
                        Log.d("SCRAPING_DEBUG", "Respaldo Firebase aplicado -> ${alerta.nombre}: ${alerta.estado}")
                    }
                }
                alertasAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SCRAPING_DEBUG", "Error al leer respaldo de Firebase: ${error.message}")
            }
        })
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