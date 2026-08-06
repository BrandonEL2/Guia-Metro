package com.example.guiametro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlertasAdapter(private val listaAlertas: List<AlertaLinea>) :
    RecyclerView.Adapter<AlertasAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreLinea)
        val txtEstado: TextView = itemView.findViewById(R.id.txtEstado)
        val txtAfectadas: TextView = itemView.findViewById(R.id.txtEstacionesAfectadas)
        val contenedorColor: View = itemView.findViewById(R.id.contenedorCuadroLinea)
        val txtNumeroLinea: TextView = itemView.findViewById(R.id.txtNumeroLinea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alerta_linea, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alerta = listaAlertas[position]

        holder.txtNombre.text = alerta.nombre.uppercase()
        holder.txtEstado.text = alerta.estado.uppercase()
        holder.txtAfectadas.text = "ESTACIONES AFECTADAS: ${alerta.estacionesAfectadas}"

        // Aplica tanto R.color como R.drawable de forma segura
        holder.contenedorColor.setBackgroundResource(alerta.fondoRes)

        // Extrae el número o letra limpio
        val numeroExtraido = alerta.nombre
            .replace(Regex("(?i)línea|linea"), "")
            .trim()

        holder.txtNumeroLinea.text = numeroExtraido
    }

    override fun getItemCount(): Int = listaAlertas.size
}