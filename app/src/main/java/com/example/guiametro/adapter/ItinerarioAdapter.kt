package com.example.guiametro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.Estacion
import com.example.guiametro.R

class ItinerarioAdapter(
    private val estaciones: List<Estacion>
) : RecyclerView.Adapter<ItinerarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtPaso: TextView = view.findViewById(R.id.txtPaso)
        val txtDetalle: TextView = view.findViewById(R.id.txtDetalle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_itinerario, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = estaciones.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val estacion = estaciones[position]

        holder.txtPaso.text = estacion.nombre

        holder.txtDetalle.text =
            "Línea ${estacion.linea}"

    }
}