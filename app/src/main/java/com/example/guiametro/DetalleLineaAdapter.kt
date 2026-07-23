package com.example.guiametro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.Estacion
import com.example.guiametro.R

class DetalleLineaAdapter(private val listaEstaciones: List<Estacion>) :
    RecyclerView.Adapter<DetalleLineaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEstacionDetalle)
        val txtCorrespondencia: TextView = view.findViewById(R.id.txtCorrespondenciasDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estacion_detalle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estacion = listaEstaciones[position]
        holder.txtNombre.text = estacion.nombre

        if (estacion.salidas.isNotEmpty()) {
            holder.txtCorrespondencia.text = "Conexiones: ${estacion.salidas.joinToString(", ")}"
            holder.txtCorrespondencia.visibility = View.VISIBLE
        } else {
            holder.txtCorrespondencia.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size
}