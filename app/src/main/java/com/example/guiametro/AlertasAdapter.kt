package com.example.guiametro

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlertasAdapter(private val listaAlertas: MutableList<AlertaLinea>) :
    RecyclerView.Adapter<AlertasAdapter.AlertaViewHolder>() {

    class AlertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreLinea: TextView = itemView.findViewById(R.id.txtNombreLinea)
        val txtEstado: TextView = itemView.findViewById(R.id.txtEstado)
        val txtEstacionesAfectadas: TextView = itemView.findViewById(R.id.txtEstacionesAfectadas)
        val viewIndicadorColor: View = itemView.findViewById(R.id.viewIndicadorColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alerta_linea, parent, false)
        return AlertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertaViewHolder, position: Int) {
        val alerta = listaAlertas[position]

        holder.txtNombreLinea.text = alerta.nombre
        holder.txtEstado.text = alerta.estado
        holder.txtEstacionesAfectadas.text = "ESTACIONES AFECTADAS: ${alerta.estacionesAfectadas}"

        try {
            if (alerta.colorHex.isNotEmpty()) {
                holder.viewIndicadorColor.setBackgroundColor(Color.parseColor(alerta.colorHex))
            }
        } catch (e: Exception) {
            // Manejo de error de color si llegara a fallar
        }
    }

    override fun getItemCount(): Int = listaAlertas.size

    fun actualizarDatos(nuevaLista: List<AlertaLinea>) {
        listaAlertas.clear()
        listaAlertas.addAll(nuevaLista)
        notifyDataSetChanged() // <-- Esto fuerza a que se pinte la tarjeta
    }
}