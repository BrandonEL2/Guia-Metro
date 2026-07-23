package com.example.guiametro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.Estacion
import com.example.guiametro.R

class EstacionesAdapter(private val listaEstaciones: List<Estacion>) :
    RecyclerView.Adapter<EstacionesAdapter.EstacionViewHolder>() {

    class EstacionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEstacion)
        val txtLinea: TextView = view.findViewById(R.id.txtLineaEstacion)
        val txtCorrespondencia: TextView = view.findViewById(R.id.txtCorrespondencia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estacion, parent, false)
        return EstacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstacionViewHolder, position: Int) {
        val estacion = listaEstaciones[position]
        holder.txtNombre.text = estacion.nombre
        holder.txtLinea.text = estacion.linea

        // Si tiene salidas o es transbordo, podemos mostrarlo; de lo contrario, ocultamos o indicamos normal
        if (estacion.esTransbordo) {
            holder.txtCorrespondencia.text = "Transbordo disponible"
        } else {
            holder.txtCorrespondencia.text = "Línea ${estacion.linea}"
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size
}