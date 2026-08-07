package com.example.guiametro

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        val context = holder.itemView.context

        holder.txtNombre.text = alerta.nombre.uppercase()
        holder.txtEstado.text = alerta.estado.uppercase()
        holder.txtAfectadas.text = "ESTACIONES AFECTADAS: ${alerta.estacionesAfectadas}"

        // 1. Color dinámico del texto de estado según gravedad
        val colorEstado = when {
            alerta.estado.contains("REGULAR", ignoreCase = true) -> Color.parseColor("#23822F")
            alerta.estado.contains("LENTA", ignoreCase = true) -> Color.parseColor("#D97706")
            else -> Color.parseColor("#B50B0B")
        }
        holder.txtEstado.setTextColor(colorEstado)

        // 2. Extraer identificador ("1", "12", "A", "B", etc.)
        val idLinea = alerta.nombre
            .replace(Regex("(?i)línea|linea"), "")
            .trim()
            .uppercase()

        holder.txtNumeroLinea.text = idLinea

        // 3. Renderizar icono (Manejo especial bicolor para Línea B)
        if (idLinea == "B") {
            val verde = ContextCompat.getColor(context, R.color.linea_b_verde)
            val gris = ContextCompat.getColor(context, R.color.linea_b_gris)

            // Fondo con división 50/50 y esquina superior derecha redondeada (14dp)
            val cornerPx = 14 * context.resources.displayMetrics.density
            val gradientBicolor = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(verde, verde, gris, gris)
            ).apply {
                // Cantidades de curvatura: [Top-Left, Top-Right, Bottom-Right, Bottom-Left]
                cornerRadii = floatArrayOf(0f, 0f, cornerPx, cornerPx, 0f, 0f, 0f, 0f)
            }

            holder.contenedorColor.background = gradientBicolor
            holder.contenedorColor.backgroundTintList = null
        } else {
            // Líneas unicolor: Usar bg_icono_linea + color de colors.xml
            holder.contenedorColor.setBackgroundResource(R.drawable.bg_icono_linea)
            val colorRes = obtenerColorResId(idLinea)
            val colorOficial = ContextCompat.getColor(context, colorRes)
            holder.contenedorColor.backgroundTintList = ColorStateList.valueOf(colorOficial)
        }
    }

    override fun getItemCount(): Int = listaAlertas.size

    // Mapeo directo a los recursos de colors.xml
    private fun obtenerColorResId(idLinea: String): Int {
        return when (idLinea) {
            "1" -> R.color.linea_1
            "2" -> R.color.linea_2
            "3" -> R.color.linea_3
            "4" -> R.color.linea_4
            "5" -> R.color.linea_5
            "6" -> R.color.linea_6
            "7" -> R.color.linea_7
            "8" -> R.color.linea_8
            "9" -> R.color.linea_9
            "12" -> R.color.linea_12
            "A" -> R.color.linea_a
            else -> R.color.linea_default
        }
    }
}