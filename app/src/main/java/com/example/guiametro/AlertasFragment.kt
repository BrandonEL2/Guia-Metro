package com.example.guiametro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnyRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

        // 2. Botón para forzar actualización manual inmediata
        val btnActualizar = view.findViewById<View>(R.id.btnActualizar)
        btnActualizar?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                consultarEstadoOficialMetro()
            }
        }

        // 3. Botón para abrir la pantalla de Programar Alertas
        val fabProgramarAlerta = view.findViewById<View>(R.id.fabProgramarAlerta)
        fabProgramarAlerta?.setOnClickListener {
            val intent = Intent(requireContext(), ProgramarAlertaActivity::class.java)
            startActivity(intent)
        }

        // 4. Iniciar ciclo en tiempo real controlado por ciclo de vida
        iniciarBucleOptimizado()
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

    // Se pausa automáticamente si la app va a segundo plano o se cambia de pestaña
    private fun iniciarBucleOptimizado() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    withContext(Dispatchers.IO) {
                        consultarEstadoOficialMetro()
                    }
                    // 120 segundos (2 minutos) de espera mientras el usuario mira la pantalla
                    delay(120_000)
                }
            }
        }
    }

    private suspend fun consultarEstadoOficialMetro() {
        try {
            Log.d("SCRAPING_DEBUG", "Iniciando escaneo multi-alerta...")
            val urlPrincipal = "https://www.metro.cdmx.gob.mx/la-red/estado-del-servicio"

            // 1. Obtener la página contenedora y la URL del iframe
            val docPrincipal = Jsoup.connect(urlPrincipal)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(10000)
                .get()

            val urlIframe = docPrincipal.select("iframe").firstOrNull()?.attr("abs:src") ?: urlPrincipal

            // 2. Cargar Página 1 (contiene filas 1 a 10)
            val responsePag1 = Jsoup.connect(urlIframe)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(10000)
                .execute()

            val docIframe1 = responsePag1.parse()
            val cookiesSesion = responsePag1.cookies()
            val viewState = docIframe1.select("input[name$=ViewState]").`val`() ?: ""

            // 3. Obtener Página 2 mediante POST AJAX (contiene filas 11 en adelante)
            val filasPagina2 = mutableListOf<org.jsoup.nodes.Element>()
            if (viewState.isNotEmpty()) {
                try {
                    val responseXml = Jsoup.connect(urlIframe)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .timeout(10000)
                        .cookies(cookiesSesion)
                        .header("Faces-Request", "partial/ajax")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .parser(org.jsoup.parser.Parser.xmlParser())
                        .data("jakarta.faces.partial.ajax", "true")
                        .data("jakarta.faces.source", "frmEstadoServicio:tblEstadoServicio")
                        .data("jakarta.faces.partial.execute", "frmEstadoServicio:tblEstadoServicio")
                        .data("jakarta.faces.partial.render", "frmEstadoServicio:tblEstadoServicio")
                        .data("frmEstadoServicio:tblEstadoServicio", "frmEstadoServicio:tblEstadoServicio")
                        .data("frmEstadoServicio:tblEstadoServicio_pagination", "true")
                        .data("frmEstadoServicio:tblEstadoServicio_first", "10")
                        .data("frmEstadoServicio:tblEstadoServicio_rows", "10")
                        .data("frmEstadoServicio:tblEstadoServicio_skipChildren", "true")
                        .data("frmEstadoServicio:tblEstadoServicio_encodeFeature", "true")
                        .data("frmEstadoServicio", "frmEstadoServicio")
                        .data("frmEstadoServicio:tblEstadoServicio_columnOrder", "frmEstadoServicio:tblEstadoServicio:j_idt29,frmEstadoServicio:tblEstadoServicio:j_idt30,frmEstadoServicio:tblEstadoServicio:j_idt32,frmEstadoServicio:tblEstadoServicio:j_idt34")
                        .data("jakarta.faces.ViewState", viewState)
                        .post()

                    val xmlDoc = Jsoup.parse(responseXml.html(), "", org.jsoup.parser.Parser.xmlParser())
                    val cdataHtml = xmlDoc.select("update").firstOrNull { it.attr("id").contains("tblEstadoServicio") }?.text()
                        ?: xmlDoc.select("update").firstOrNull()?.text() ?: ""

                    if (cdataHtml.isNotEmpty()) {
                        val docPag2 = Jsoup.parse(cdataHtml)
                        filasPagina2.addAll(docPag2.select("tr"))
                    }
                } catch (e: Exception) {
                    Log.e("SCRAPING_DEBUG", "Error obteniendo Página 2: ${e.message}")
                }
            }

            // 4. Procesar la unión de filas de ambas páginas
            val todasLasFilas = docIframe1.select("tr") + filasPagina2
            var huboCambios = false
            val lineasFaltantes = mutableListOf<AlertaLinea>()

            for (alerta in listaAlertas) {
                val idLinea = alerta.nombre.replace(Regex("(?i)línea|linea"), "").trim().lowercase()
                val estadosEncontrados = mutableListOf<String>()
                val afectadasEncontradas = mutableListOf<String>()

                for (fila in todasLasFilas) {
                    val celdas = fila.select("td")
                    if (celdas.size >= 2) {
                        val htmlCelda0 = celdas[0].outerHtml().lowercase()
                        val textoCelda0 = celdas[0].text().trim().lowercase()
                        val estadoTexto = celdas[1].text().trim()
                        val afectadasTexto = if (celdas.size >= 3) celdas[2].text().trim() else ""
                        val infoAdicional = if (celdas.size >= 4) celdas[3].text().trim() else ""

                        if (estadoTexto.isEmpty() || estadoTexto.contains("estado", ignoreCase = true)) continue

                        val coincide = when (idLinea) {
                            "a" -> htmlCelda0.contains(Regex("stc[_-]?a\\b|linea[_-]?a\\b")) || textoCelda0 == "a" || textoCelda0 == "línea a"
                            "b" -> htmlCelda0.contains(Regex("stc[_-]?b\\b|linea[_-]?b\\b")) || textoCelda0 == "b" || textoCelda0 == "línea b"
                            "12" -> htmlCelda0.contains(Regex("stc[_-]?12\\b|linea[_-]?12\\b")) || textoCelda0.contains("12")
                            else -> htmlCelda0.contains(Regex("stc[_-]?$idLinea\\b|linea[_-]?$idLinea\\b")) || Regex("(^|\\D)$idLinea(\\D|$)").containsMatchIn(textoCelda0)
                        }

                        if (coincide) {
                            val estadoUpper = estadoTexto.uppercase()
                            if (!estadosEncontrados.contains(estadoUpper)) {
                                estadosEncontrados.add(estadoUpper)
                            }

                            val detalleCompleto = when {
                                afectadasTexto.isNotEmpty() && infoAdicional.isNotEmpty() -> "$afectadasTexto ($infoAdicional)"
                                afectadasTexto.isNotEmpty() -> afectadasTexto
                                infoAdicional.isNotEmpty() -> infoAdicional
                                else -> "Ninguna"
                            }

                            if (!afectadasEncontradas.contains(detalleCompleto)) {
                                afectadasEncontradas.add(detalleCompleto)
                            }
                        }
                    }
                }

                if (estadosEncontrados.isNotEmpty()) {
                    val nuevoEstado = estadosEncontrados.joinToString("\n")
                    val nuevasAfectadas = afectadasEncontradas.joinToString(" / ")

                    if (alerta.estado != nuevoEstado || alerta.estacionesAfectadas != nuevasAfectadas) {
                        alerta.estado = nuevoEstado
                        alerta.estacionesAfectadas = nuevasAfectadas
                        huboCambios = true
                    }
                } else {
                    lineasFaltantes.add(alerta)
                }
            }

            if (lineasFaltantes.isNotEmpty()) {
                sincronizarLineasFaltantesFirebase(lineasFaltantes)
            }

            if (huboCambios) {
                withContext(Dispatchers.Main) {
                    alertasAdapter.notifyDataSetChanged()
                }
            }

        } catch (e: Exception) {
            Log.e("SCRAPING_ERROR", "Falla de red, usando Firestore: ${e.message}")
            withContext(Dispatchers.Main) {
                sincronizarEstadosFirebase()
            }
        }
    }

    private fun sincronizarLineasFaltantesFirebase(faltantes: List<AlertaLinea>) {
        db.collection("estado_lineas")
            .get()
            .addOnSuccessListener { documents ->
                var huboCambios = false
                for (alerta in faltantes) {
                    val document = documents.find { doc ->
                        val nombreFB = doc.getString("nombre") ?: ""
                        val localLimpio = alerta.nombre.replace("í", "i").replace("Í", "I")
                        val fbLimpio = nombreFB.replace("í", "i").replace("Í", "I")
                        localLimpio.equals(fbLimpio, ignoreCase = true)
                    }

                    if (document != null) {
                        val estadoFb = (document.getString("estado") ?: "SERVICIO REGULAR").uppercase()
                        val afectadasFb = document.getString("estacionesAfectadas") ?: "Ninguna"

                        if (alerta.estado != estadoFb || alerta.estacionesAfectadas != afectadasFb) {
                            alerta.estado = estadoFb
                            alerta.estacionesAfectadas = afectadasFb
                            huboCambios = true
                        }
                    }
                }

                if (huboCambios) {
                    alertasAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                Log.e("SCRAPING_DEBUG", "Error al leer respaldo en Firestore: ${e.message}")
            }
    }

    private fun sincronizarEstadosFirebase() {
        db.collection("estado_lineas")
            .get()
            .addOnSuccessListener { documents ->
                var huboCambios = false
                for (document in documents) {
                    val nombreFirebase = document.getString("nombre") ?: continue
                    val estadoFirebase = document.getString("estado") ?: "SERVICIO REGULAR"
                    val afectadasFirebase = document.getString("estacionesAfectadas") ?: "Ninguna"

                    val itemEncontrado = listaAlertas.find { item ->
                        val localLimpio = item.nombre.replace("í", "i").replace("Í", "I")
                        val fbLimpio = nombreFirebase.replace("í", "i").replace("Í", "I")
                        localLimpio.equals(fbLimpio, ignoreCase = true)
                    }

                    if (itemEncontrado != null && (itemEncontrado.estado != estadoFirebase || itemEncontrado.estacionesAfectadas != afectadasFirebase)) {
                        itemEncontrado.estado = estadoFirebase
                        itemEncontrado.estacionesAfectadas = afectadasFirebase
                        huboCambios = true
                    }
                }

                if (huboCambios) {
                    activity?.runOnUiThread {
                        alertasAdapter.notifyDataSetChanged()
                    }
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