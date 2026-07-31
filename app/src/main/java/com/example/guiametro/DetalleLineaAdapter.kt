package com.example.guiametro.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.Estacion
import com.example.guiametro.R

class DetalleLineaAdapter(private val listaEstaciones: List<Estacion>) :
    RecyclerView.Adapter<DetalleLineaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEstacionDetalle)
        val txtCorrespondencia: TextView = view.findViewById(R.id.txtCorrespondenciasDetalle)
        val viewPuntoEstacion: View = view.findViewById(R.id.viewPuntoEstacion)
        val viewLineaArriba: View = view.findViewById(R.id.viewLineaArriba)
        val viewLineaAbajo: View = view.findViewById(R.id.viewLineaAbajo)
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

        // Comprobamos si la estación pertenece a la Línea B
        if (estacion.linea.uppercase() == "LB") {
            // Aplicamos el diseño bicolor que creamos
            holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_bicolor)

            // Las líneas conectoras toman el color verde superior de referencia
            val colorVerde = ContextCompat.getColor(holder.itemView.context, R.color.linea_b_verde)
            holder.viewLineaArriba.setBackgroundColor(colorVerde)
            holder.viewLineaAbajo.setBackgroundColor(colorVerde)
        } else {
            // Para el resto de las líneas normales
            val colorResId = when (estacion.linea.uppercase()) {
                "L1" -> R.color.linea_1
                "L2" -> R.color.linea_2
                "L3" -> R.color.linea_3
                "L4" -> R.color.linea_4
                "L5" -> R.color.linea_5
                "L6" -> R.color.linea_6
                "L7" -> R.color.linea_7
                "L8" -> R.color.linea_8
                "L9" -> R.color.linea_9
                "L12" -> R.color.linea_12
                "LA" -> R.color.linea_a
                else -> R.color.linea_default
            }

            val colorInt = ContextCompat.getColor(holder.itemView.context, colorResId)

            // Restauramos el fondo base ovalado para las líneas de un solo color
            holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_estacions)
            val drawablePunto = holder.viewPuntoEstacion.background as? GradientDrawable
            drawablePunto?.setColor(colorInt)

            holder.viewLineaArriba.setBackgroundColor(colorInt)
            holder.viewLineaAbajo.setBackgroundColor(colorInt)
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size
}