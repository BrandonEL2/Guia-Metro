package com.example.guiametro.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
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
        val puntoEstacion: View = view.findViewById(R.id.puntoEstacion)
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

        // Asignamos el color oficial correspondiente a la línea
        cambiarColorPuntoYLinea(holder.puntoEstacion, holder.lineaConectora, estacion.linea)

        val esOrigen = (position == 0)
        val esDestino = (position == listaEstaciones.size - 1)

        val siguienteEstacion = if (!esDestino) listaEstaciones[position + 1] else null
        val esTransbordoReal = siguienteEstacion != null && (estacion.nombre == siguienteEstacion.nombre && estacion.linea != siguienteEstacion.linea)

        if (esOrigen || esDestino || esTransbordoReal) {
            holder.txtNombreEstacion.textSize = 17f
            holder.txtNombreEstacion.setTypeface(null, Typeface.BOLD)
            holder.txtInstruccionDetalle.textSize = 13f
        } else {
            holder.txtNombreEstacion.textSize = 14f
            holder.txtNombreEstacion.setTypeface(null, Typeface.NORMAL)
            holder.txtInstruccionDetalle.textSize = 12f
        }

        if (esDestino) {
            holder.txtInstruccionDetalle.text = "¡Has Llegado!\nDestino final alcanzado."
            holder.lineaConectora.visibility = View.GONE
            return
        }

        when {
            esOrigen -> {
                val direccion = obtenerDireccionTerminal(estacion, siguienteEstacion!!)
                holder.txtInstruccionDetalle.text = "Aborda el Metro\nDirección: $direccion"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            esTransbordoReal -> {
                holder.txtInstruccionDetalle.text = "⚠️ Bájate aquí para Transbordar\nCamina hacia Línea ${siguienteEstacion!!.linea} • ~5 min"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            position > 0 && listaEstaciones[position - 1].nombre == estacion.nombre && listaEstaciones[position - 1].linea != estacion.linea -> {
                val direccion = obtenerDireccionTerminal(estacion, siguienteEstacion!!)
                holder.txtInstruccionDetalle.text = "Aborda Línea ${estacion.linea}\nDirección: $direccion"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            else -> {
                holder.txtInstruccionDetalle.text = "Sigue en el Metro • Línea ${estacion.linea}"
                holder.lineaConectora.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size

    /**
     * Pinta el punto de la estación y la línea conectora con el color oficial de la CDMX.
     */
    private fun cambiarColorPuntoYLinea(punto: View, linea: View, nombreLinea: String) {
        val colorHex = when (nombreLinea.uppercase()) {
            "L1" -> "#FF7F00" // Rosa / Naranja característica L1
            "L2" -> "#0047AB" // Azul
            "L3" -> "#008000" // Verde
            "L4" -> "#00A8B5" // Cyan / Rosado claro
            "L5" -> "#FFD700" // Amarillo
            "L6" -> "#FF0000" // Rojo
            "L7" -> "#FF6600" // Naranja
            "L8" -> "#008033" // Verde oscuro
            "L9" -> "#7C3F00" // Café
            "L12" -> "#B8860B" // Dorado / Amarillo oscuro
            "LA" -> "#8A2BE2" // Morado
            "LB" -> "#808080" // Gris
            else -> "#2E7D32"   // Color por defecto
        }

        val colorInt = Color.parseColor(colorHex)

        // Pintar el punto circular
        val drawablePunto = punto.background as? GradientDrawable
        drawablePunto?.setColor(colorInt)

        // Pintar la barra conectora vertical
        linea.setBackgroundColor(colorInt)
    }

    private fun obtenerDireccionTerminal(actual: Estacion, siguiente: Estacion): String {
        val vaHaciaAdelante = siguiente.id > actual.id

        return when (actual.linea.uppercase()) {
            "L1" -> if (vaHaciaAdelante) "Pantitlán" else "Observatorio"
            "L2" -> if (vaHaciaAdelante) "Tasqueña" else "Cuatro Caminos"
            "L3" -> if (vaHaciaAdelante) "Universidad" else "Indios Verdes"
            "L4" -> if (vaHaciaAdelante) "Santa Anita" else "Martín Carrera"
            "L5" -> if (vaHaciaAdelante) "Pantitlán" else "Politécnico"
            "L6" -> if (vaHaciaAdelante) "Martín Carrera" else "El Rosario"
            "L7" -> if (vaHaciaAdelante) "Barranca del Muerto" else "El Rosario"
            "L8" -> if (vaHaciaAdelante) "Constitución de 1917" else "Garibaldi"
            "L9" -> if (vaHaciaAdelante) "Pantitlán" else "Tacubaya"
            "L12" -> if (vaHaciaAdelante) "Tláhuac" else "Mixcoac"
            "LA" -> if (vaHaciaAdelante) "La Paz" else "Pantitlán"
            "LB" -> if (vaHaciaAdelante) "Buenavista" else "Ciudad Azteca"
            else -> "Fin de línea"
        }
    }
}