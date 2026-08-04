package com.example.guiametro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

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

        alertasAdapter = AlertasAdapter(listaAlertas)
        recyclerView.adapter = alertasAdapter

        escucharEstadoMetroEnTiempoReal()
    }

    private fun escucharEstadoMetroEnTiempoReal() {
        db.collection("estado_lineas")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("FIRESTORE_ERROR", "Error al escuchar datos: ${e.message}")
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    listaAlertas.clear()
                    Log.d("FIRESTORE_DATA", "Documentos encontrados: ${snapshots.documents.size}")

                    for (document in snapshots.documents) {
                        val alerta = document.toObject(AlertaLinea::class.java)
                        if (alerta != null) {
                            listaAlertas.add(alerta)
                            Log.d("FIRESTORE_DATA", "Línea cargada: ${alerta.nombre}")
                        } else {
                            Log.w("FIRESTORE_DATA", "No se pudo mapear el documento: ${document.id}")
                        }
                    }

                    // Actualizamos el adaptador de forma segura en el hilo principal
                    activity?.runOnUiThread {
                        alertasAdapter.actualizarDatos(listaAlertas)
                    }
                } else {
                    Log.d("FIRESTORE_DATA", "El snapshot está vacío")
                }
            }
    }
}