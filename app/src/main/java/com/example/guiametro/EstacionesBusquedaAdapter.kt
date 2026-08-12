package com.example.guiametro

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

data class EstacionBusqueda(
    val nombre: String,
    val idLinea: String,
    val detalleLinea: String,
    val conexiones: String = "" // Ej: "Línea 3, Línea 5"
)

class EstacionesBusquedaAdapter(
    private val listaCompleta: List<EstacionBusqueda>,
    private val onItemClick: (EstacionBusqueda) -> Unit
) : RecyclerView.Adapter<EstacionesBusquedaAdapter.ViewHolder>() {

    private var listaFiltrada: List<EstacionBusqueda> = ArrayList(listaCompleta)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreEstacion)
        val txtDetalle: TextView = itemView.findViewById(R.id.txtDetalleLinea)
        val txtNumeroLinea: TextView = itemView.findViewById(R.id.txtNumeroLinea)
        val contenedorColor: View = itemView.findViewById(R.id.contenedorCuadroLinea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estacion_busqueda, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estacion = listaFiltrada[position]
        val context = holder.itemView.context

        holder.txtNombre.text = estacion.nombre

        // Muestra el detalle y las conexiones si existen
        if (estacion.conexiones.isNotEmpty()) {
            holder.txtDetalle.text = "Línea ${estacion.idLinea} • ${estacion.detalleLinea}\n🔄 Transbordo con ${estacion.conexiones}"
        } else {
            holder.txtDetalle.text = "Línea ${estacion.idLinea} • ${estacion.detalleLinea}"
        }

        holder.txtNumeroLinea.text = estacion.idLinea

        val colorRes = when (estacion.idLinea) {
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
            "B" -> R.color.linea_b_verde
            else -> R.color.linea_default
        }
        val color = ContextCompat.getColor(context, colorRes)
        holder.contenedorColor.backgroundTintList = ColorStateList.valueOf(color)

        holder.itemView.setOnClickListener { onItemClick(estacion) }
    }

    override fun getItemCount(): Int = listaFiltrada.size

    fun filtrar(query: String) {
        val queryLimpia = normalizarTexto(query)

        listaFiltrada = if (queryLimpia.isEmpty()) {
            emptyList()
        } else {
            listaCompleta.filter { estacion ->
                // Evalúa SOLO el nombre, ignorando acentos y exigiendo que empiece exactamente con la query
                normalizarTexto(estacion.nombre).startsWith(queryLimpia)
            }
        }
        notifyDataSetChanged()
    }

    // Función auxiliar para quitar acentos y pasar a minúsculas
    private fun normalizarTexto(texto: String): String {
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val normalizado = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
        return regex.replace(normalizado, "").trim().lowercase()
    }
}