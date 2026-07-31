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

        val nombreEstacion = estacion.nombre.uppercase().trim()
        val lineaActual = estacion.linea.uppercase().trim()

        // Obtenemos el color dinámico correspondiente a la línea que se está visualizando
        val colorLineaActual = obtenerColorDeLinea(lineaActual)

        when (nombreEstacion) {
            "ATRALILCO", "ATLALILCO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_atlalilco_l8)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "BALDERAS" -> {
                when (lineaActual) {
                    "L3", "3" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_centro_medico_l3)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_balderas)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "BELLAS ARTES" -> {
                when (lineaActual) {
                    "L2", "2" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_bellas_artes_l2)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> pintarEstacionNormal(holder, lineaActual)
                }
            }
            "CANDELARIA" -> {
                when (lineaActual) {
                    "L4", "4" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_candelaria_l4)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_candelaria)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "CENTRO MEDICO", "CENTRO MÉDICO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_centro_medico_l3)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "CHABACANO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_chabacano_l2)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "CONSULADO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_consulado_l4)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "DEPORTIVO 18 DE MARZO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_dep_18_marzo_l3)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "EL ROSARIO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_el_rosario_l6)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "ERMITA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_ermita_l2)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "GUERRERO" -> {
                when (lineaActual) {
                    "LB", "B" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_guerrero_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_guerrero_l3)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "HIDALGO" -> {
                when (lineaActual) {
                    "L3", "3" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_hidalgo_l3)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_hidalgo_l2)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "INSTITUTO DEL PETROLEO", "INSTITUTO DEL PETRÓLEO" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_instituto_petroleo_l5)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "JAMAICA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_jamaica_l4)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "LA RAZA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_la_raza_l3)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "MARTIN CARRERA", "MARTÍN CARRERA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_martin_carrera_l4)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "MIXCOAC" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_mixcoac_l7)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "MORELOS" -> {
                when (lineaActual) {
                    "LB", "B" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_morelos_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_morelos_l4)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "OCEANIA", "OCEANÍA" -> {
                when (lineaActual) {
                    "LB", "B" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_oceania_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_oceania_l5)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "PANTITLAN", "PANTITLÁN" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_pantitlan)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "PINO SUAREZ", "PINO SUÁREZ" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_pino_suarez)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "SALTO DEL AGUA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_salto_agua)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "SAN LAZARO", "SAN LÁZARO" -> {
                when (lineaActual) {
                    "LB", "B" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_san_lazaro_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_san_lazaro)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "SANTA ANITA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_santa_anita_l4)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "TACUBA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_tacuba_l2)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "TACUBAYA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_tricolor)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            "GARIBALDI/LAGUNILLA", "GARIBALDI" -> {
                when (lineaActual) {
                    "LB", "B" -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_garibaldi_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                    else -> {
                        holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_garibaldi_lb)
                        configurarLineasConectoras(holder, colorLineaActual)
                    }
                }
            }
            "ZAPATA" -> {
                holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_zapata_l3)
                configurarLineasConectoras(holder, colorLineaActual)
            }
            else -> {
                pintarEstacionNormal(holder, lineaActual)
            }
        }
    }

    // Métodos auxiliares
    private fun pintarEstacionNormal(holder: ViewHolder, linea: String) {
        val colorResId = obtenerColorDeLinea(linea)
        if (linea == "LB" || linea == "B") {
            holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_bicolor)
            configurarLineasConectoras(holder, colorResId)
        } else {
            val colorInt = ContextCompat.getColor(holder.itemView.context, colorResId)

            holder.viewPuntoEstacion.setBackgroundResource(R.drawable.bg_punto_estacions)
            val drawablePunto = holder.viewPuntoEstacion.background as? GradientDrawable
            drawablePunto?.setColor(colorInt)

            configurarLineasConectoras(holder, colorResId)
        }
    }

    private fun configurarLineasConectoras(holder: ViewHolder, colorResId: Int) {
        val colorInt = ContextCompat.getColor(holder.itemView.context, colorResId)
        holder.viewLineaArriba.setBackgroundColor(colorInt)
        holder.viewLineaAbajo.setBackgroundColor(colorInt)
    }

    private fun obtenerColorDeLinea(linea: String): Int {
        return when (linea.uppercase().trim()) {
            "L1", "1" -> R.color.linea_1
            "L2", "2" -> R.color.linea_2
            "L3", "3" -> R.color.linea_3
            "L4", "4" -> R.color.linea_4
            "L5", "5" -> R.color.linea_5
            "L6", "6" -> R.color.linea_6
            "L7", "7" -> R.color.linea_7
            "L8", "8" -> R.color.linea_8
            "L9", "9" -> R.color.linea_9
            "L12", "12" -> R.color.linea_12
            "LA", "A" -> R.color.linea_a
            "LB", "B" -> R.color.linea_b_verde
            else -> R.color.linea_default
        }
    }


    override fun getItemCount(): Int = listaEstaciones.size
}