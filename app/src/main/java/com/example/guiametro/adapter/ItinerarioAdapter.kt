package com.example.guiametro.adapter

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        val context = holder.itemView.context
        val numeroPaso = position + 1

        val esOrigen = (position == 0)
        val esDestino = (position == listaEstaciones.size - 1)
        val siguienteEstacion = if (!esDestino) listaEstaciones[position + 1] else null
        val estacionAnterior = if (position > 0) listaEstaciones[position - 1] else null

        // Detectar si la estación actual es el punto de salida de un transbordo
        val esTransbordoSalida = siguienteEstacion != null &&
                (estacion.nombre == siguienteEstacion.nombre && estacion.linea != siguienteEstacion.linea)

        // Detectar si la estación actual es el punto de entrada a la nueva línea tras transbordar
        val esTransbordoEntrada = estacionAnterior != null &&
                (estacion.nombre == estacionAnterior.nombre && estacion.linea != estacionAnterior.linea)

        holder.txtNumeroPaso.text = "$numeroPaso."
        holder.txtNombreEstacion.text = estacion.nombre

        // Estilos para destacar nodos clave (Origen, Destino, Transbordos)
        if (esOrigen || esDestino || esTransbordoSalida || esTransbordoEntrada) {
            holder.txtNombreEstacion.textSize = 17f
            holder.txtNombreEstacion.setTypeface(null, Typeface.BOLD)
            holder.txtInstruccionDetalle.textSize = 13f
        } else {
            holder.txtNombreEstacion.textSize = 15f
            holder.txtNombreEstacion.setTypeface(null, Typeface.NORMAL)
            holder.txtInstruccionDetalle.textSize = 12f
        }

        // 1. Asignar el icono vectorial
        val drawablePunto = obtenerDrawablePunto(context, estacion)
        if (drawablePunto != null) {
            holder.puntoEstacion.background = drawablePunto
            holder.puntoEstacion.backgroundTintList = null
        } else {
            holder.puntoEstacion.setBackgroundResource(R.drawable.bg_punto_estacions)
            val colorLinea = obtenerColorLinea(context, estacion.linea)
            (holder.puntoEstacion.background as? GradientDrawable)?.setColor(colorLinea)
        }

        // 2. Asignar color a la línea vertical
        val colorLinea = obtenerColorLinea(context, estacion.linea)
        val idNormalizado = estacion.linea.uppercase().replace("LÍNEA", "").replace("LINEA", "").trim()

        if (idNormalizado == "B" || idNormalizado == "LB") {
            holder.lineaConectora.background = ContextCompat.getDrawable(context, R.drawable.bg_linea_b)
            holder.lineaConectora.backgroundTintList = null
        } else {
            holder.lineaConectora.setBackgroundColor(colorLinea)
        }

        // 3. Generación de instrucciones claras de descenso y dirección
        when {
            esDestino -> {
                holder.txtInstruccionDetalle.text = "¡Bájate aquí! • Destino final alcanzado"
                holder.lineaConectora.visibility = View.INVISIBLE
            }

            esTransbordoSalida -> {
                holder.txtInstruccionDetalle.text = "⚠️ Bájate aquí • Transborda hacia Línea ${siguienteEstacion!!.linea}"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            esTransbordoEntrada -> {
                val direccion = if (siguienteEstacion != null) obtenerDireccionTerminal(estacion, siguienteEstacion) else "Terminal"
                holder.txtInstruccionDetalle.text = "Aborda Línea ${estacion.linea} • Dirección $direccion"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            esOrigen -> {
                val direccion = if (siguienteEstacion != null) obtenerDireccionTerminal(estacion, siguienteEstacion) else "Terminal"
                holder.txtInstruccionDetalle.text = "Aborda Línea ${estacion.linea} • Dirección $direccion"
                holder.lineaConectora.visibility = View.VISIBLE
            }

            else -> {
                holder.txtInstruccionDetalle.text = "Permanece en el vagón • Línea ${estacion.linea}"
                holder.lineaConectora.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = listaEstaciones.size

    private fun obtenerDrawablePunto(context: Context, estacion: Estacion): Drawable? {
        val nombreNormalizado = estacion.nombre.lowercase()
            .replace(" ", "_")
            .replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
            .replace("ñ", "n")
            .replace("deportivo_18_de_marzo", "dep_18_marzo")
            .replace("18_de_marzo", "dep_18_marzo")
            .replace("instituto_del_petroleo", "instituto_petroleo")

        val lineaNormalizada = estacion.linea.lowercase()
            .replace("línea", "").replace("linea", "").trim()
            .let { if (it.startsWith("l")) it else "l$it" }

        val nombreConLinea = "bg_punto_${nombreNormalizado}_${lineaNormalizada}"
        var resId = context.resources.getIdentifier(nombreConLinea, "drawable", context.packageName)

        if (resId == 0) {
            val nombreSimple = "bg_punto_${nombreNormalizado}"
            resId = context.resources.getIdentifier(nombreSimple, "drawable", context.packageName)
        }

        return if (resId != 0) ContextCompat.getDrawable(context, resId) else null
    }

    private fun obtenerColorLinea(context: Context, nombreLinea: String): Int {
        val idNormalizado = nombreLinea.uppercase().replace("LÍNEA", "").replace("LINEA", "").trim()

        val colorRes = when (idNormalizado) {
            "1", "L1" -> R.color.linea_1
            "2", "L2" -> R.color.linea_2
            "3", "L3" -> R.color.linea_3
            "4", "L4" -> R.color.linea_4
            "5", "L5" -> R.color.linea_5
            "6", "L6" -> R.color.linea_6
            "7", "L7" -> R.color.linea_7
            "8", "L8" -> R.color.linea_8
            "9", "L9" -> R.color.linea_9
            "12", "L12" -> R.color.linea_12
            "A", "LA" -> R.color.linea_a
            "B", "LB" -> R.color.linea_b_verde
            else -> R.color.linea_default
        }

        return ContextCompat.getColor(context, colorRes)
    }

    private fun obtenerDireccionTerminal(actual: Estacion, siguiente: Estacion): String {
        val vaHaciaAdelante = siguiente.id > actual.id
        val idNormalizado = actual.linea.uppercase().replace("LÍNEA", "").replace("LINEA", "").trim()

        return when (idNormalizado) {
            "1", "L1" -> if (vaHaciaAdelante) "Pantitlán" else "Observatorio"
            "2", "L2" -> if (vaHaciaAdelante) "Tasqueña" else "Cuatro Caminos"
            "3", "L3" -> if (vaHaciaAdelante) "Universidad" else "Indios Verdes"
            "4", "L4" -> if (vaHaciaAdelante) "Santa Anita" else "Martín Carrera"
            "5", "L5" -> if (vaHaciaAdelante) "Pantitlán" else "Politécnico"
            "6", "L6" -> if (vaHaciaAdelante) "Martín Carrera" else "El Rosario"
            "7", "L7" -> if (vaHaciaAdelante) "Barranca del Muerto" else "El Rosario"
            "8", "L8" -> if (vaHaciaAdelante) "Constitución de 1917" else "Garibaldi"
            "9", "L9" -> if (vaHaciaAdelante) "Pantitlán" else "Tacubaya"
            "12", "L12" -> if (vaHaciaAdelante) "Tláhuac" else "Mixcoac"
            "A", "LA" -> if (vaHaciaAdelante) "La Paz" else "Pantitlán"
            "B", "LB" -> if (vaHaciaAdelante) "Buenavista" else "Ciudad Azteca"
            else -> "Terminal"
        }
    }
}