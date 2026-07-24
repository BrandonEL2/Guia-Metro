package com.example.guiametro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.Estacion
import com.example.guiametro.R

class ItinerarioAdapter(private val listaEstaciones: List<Estacion>) :
    RecyclerView.Adapter<ItinerarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNumeroPaso: TextView = view.findViewById(R.id.txtNumeroPaso)
        val txtNombreEstacion: TextView = view.findViewById(R.id.txtNombreEstacion)
        val txtInstruccionDetalle: TextView = view.findViewById(R.id.txtInstruccionDetalle)
        val lineaConectora: View = view.findViewById(R.id.lineaConectora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paso_itinerario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estacion = listaEstaciones[position]
        val numeroPaso = position + 1

        holder.txtNumeroPaso.text = "$numeroPaso."
        holder.txtNombreEstacion.text = "${estacion.nombre} (${estacion.linea})"

        // Verificamos si es la última estación (Destino final)
        if (position == listaEstaciones.size - 1) {
            holder.txtInstruccionDetalle.text = "¡Has Llegado!\nDestino final alcanzado."
            holder.lineaConectora.visibility = View.GONE
            return
        }

        // Analizamos si la siguiente estación cambia de línea (es decir, hay transbordo)
        val siguienteEstacion = listaEstaciones[position + 1]
        val esCambioDeLinea = estacion.nombre == siguienteEstacion.nombre && estacion.linea != siguienteEstacion.linea

        if (esCambioDeLinea || estacion.esTransbordo) {
            // Estilo de instrucción para transbordos con icono de advertencia
            holder.txtInstruccionDetalle.text = "⚠️ Transbordo Obligatorio\nCamina por pasillos hacia Línea ${siguienteEstacion.linea} • 5 min • Sigue señalización"
            holder.lineaConectora.visibility = View.VISIBLE
        } else {
            // Estación normal de trayecto en la misma línea
            holder.txtInstruccionDetalle.text = "Sube al Metro\nLínea ${estacion.linea}"
            holder.lineaConectora.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size
}