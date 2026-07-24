package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guiametro.adapter.DetalleLineaAdapter

class DetalleLineaFragment : Fragment(R.layout.fragment_detalle_linea) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titulo = view.findViewById<TextView>(R.id.txtTituloLinea)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvEstacionesLinea)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val nombreLinea = arguments?.getString("linea_seleccionada") ?: "LÍNEA 1"
        titulo.text = "ESTACIONES DE $nombreLinea"

        val listaEstaciones = obtenerEstacionesPorLinea(nombreLinea)

        recyclerView.adapter = DetalleLineaAdapter(listaEstaciones)
    }

    private fun obtenerEstacionesPorLinea(linea: String): List<Estacion> {
        return when (linea) {
            "LÍNEA 1" -> listOf(
                Estacion(1, "Observatorio", "L1", listOf("L12"), true),
                Estacion(2, "Tacubaya", "L1", listOf("L7", "L9"), true),
                Estacion(3, "Juanacatlán", "L1", emptyList(), false),
                Estacion(4, "Chapultepec", "L1", emptyList(), false),
                Estacion(5, "Sevilla", "L1", emptyList(), false),
                Estacion(6, "Insurgentes", "L1", emptyList(), false),
                Estacion(7, "Cuauhtémoc", "L1", emptyList(), false),
                Estacion(8, "Balderas", "L1", listOf("L3"), true),
                Estacion(9, "Salto del Agua", "L1", listOf("L8"), true),
                Estacion(10, "Isabel la Católica", "L1", emptyList(), false),
                Estacion(11, "Pino Suárez", "L1", listOf("L2"), true),
                Estacion(12, "Merced", "L1", emptyList(), false),
                Estacion(13, "Candelaria", "L1", listOf("L4"), true),
                Estacion(14, "San Lázaro", "L1", listOf("LB"), true),
                Estacion(15, "Moctezuma", "L1", emptyList(), false),
                Estacion(16, "Balbuena", "L1", emptyList(), false),
                Estacion(17, "Boulevard Puerto Aéreo", "L1", emptyList(), false),
                Estacion(18, "Gómez Farías", "L1", emptyList(), false),
                Estacion(19, "Zaragoza", "L1", emptyList(), false),
                Estacion(20, "Pantitlán", "L1", listOf("L5", "L9", "LA"), true)
            )

            "LÍNEA 2" -> listOf(
                Estacion(21, "Cuatro Caminos", "L2", emptyList(), false),
                Estacion(22, "Panteones", "L2", emptyList(), false),
                Estacion(23, "Tacuba", "L2", listOf("L7"), true),
                Estacion(24, "Cuitláhuac", "L2", emptyList(), false),
                Estacion(25, "Popotla", "L2", emptyList(), false),
                Estacion(26, "Colegio Militar", "L2", emptyList(), false),
                Estacion(27, "Normal", "L2", emptyList(), false),
                Estacion(28, "San Cosme", "L2", emptyList(), false),
                Estacion(29, "Revolución", "L2", emptyList(), false),
                Estacion(30, "Hidalgo", "L2", listOf("L3"), true),
                Estacion(31, "Bellas Artes", "L2", listOf("L8"), true),
                Estacion(32, "Allende", "L2", emptyList(), false),
                Estacion(33, "Zócalo/Tenochtitlan", "L2", emptyList(), false),
                Estacion(34, "Pino Suárez", "L2", listOf("L1"), true),
                Estacion(35, "San Antonio Abad", "L2", emptyList(), false),
                Estacion(36, "Chabacano", "L2", listOf("L8", "L9"), true),
                Estacion(37, "Viaducto", "L2", emptyList(), false),
                Estacion(38, "Xola", "L2", emptyList(), false),
                Estacion(39, "Villa de Cortés", "L2", emptyList(), false),
                Estacion(40, "Nativitas", "L2", emptyList(), false),
                Estacion(41, "Portales", "L2", emptyList(), false),
                Estacion(42, "Ermita", "L2", listOf("L12"), true),
                Estacion(43, "General Anaya", "L2", emptyList(), false),
                Estacion(44, "Tasqueña", "L2", emptyList(), false)
            )

            "LÍNEA 3" -> listOf(
                Estacion(45, "Indios Verdes", "L3", emptyList(), false),
                Estacion(46, "Deportivo 18 de Marzo", "L3", listOf("L6"), true),
                Estacion(47, "Potrero", "L3", emptyList(), false),
                Estacion(48, "La Raza", "L3", listOf("L5"), true),
                Estacion(49, "Tlatelolco", "L3", emptyList(), false),
                Estacion(50, "Guerrero", "L3", listOf("LB"), true),
                Estacion(51, "Hidalgo", "L3", listOf("L2"), true),
                Estacion(52, "Juárez", "L3", emptyList(), false),
                Estacion(53, "Balderas", "L3", listOf("L1"), true),
                Estacion(54, "Niños Héroes", "L3", emptyList(), false),
                Estacion(55, "Hospital General", "L3", emptyList(), false),
                Estacion(56, "Centro Médico", "L3", listOf("L9"), true),
                Estacion(57, "Etiopía/Plaza de la Transparencia", "L3", emptyList(), false),
                Estacion(58, "Eugenia", "L3", emptyList(), false),
                Estacion(59, "División del Norte", "L3", emptyList(), false),
                Estacion(60, "Zapata", "L3", listOf("L12"), true),
                Estacion(61, "Coyoacán", "L3", emptyList(), false),
                Estacion(62, "Viveros/Derechos Humanos", "L3", emptyList(), false),
                Estacion(63, "Miguel Ángel de Quevedo", "L3", emptyList(), false),
                Estacion(64, "Copilco", "L3", emptyList(), false),
                Estacion(65, "Universidad", "L3", emptyList(), false)
            )

            "LÍNEA 4" -> listOf(
                Estacion(66, "Martín Carrera", "L4", listOf("L6"), true),
                Estacion(67, "Talismán", "L4", emptyList(), false),
                Estacion(68, "Bondojito", "L4", emptyList(), false),
                Estacion(69, "Consulado", "L4", listOf("L5"), true),
                Estacion(70, "Canal del Norte", "L4", emptyList(), false),
                Estacion(71, "Morelos", "L4", listOf("LB"), true),
                Estacion(72, "Candelaria", "L4", listOf("L1"), true),
                Estacion(73, "Fray Servando", "L4", emptyList(), false),
                Estacion(74, "Jamaiquita", "L4", emptyList(), false), // Jamaica
                Estacion(75, "Santa Anita", "L4", listOf("L8"), true)
            )

            "LÍNEA 5" -> listOf(
                Estacion(76, "Politécnico", "L5", emptyList(), false),
                Estacion(77, "Instituto del Petróleo", "L5", listOf("L6"), true),
                Estacion(78, "Autobuses del Norte", "L5", emptyList(), false),
                Estacion(79, "La Raza", "L5", listOf("L3"), true),
                Estacion(80, "Misterios", "L5", emptyList(), false),
                Estacion(81, "Valle Gómez", "L5", emptyList(), false),
                Estacion(82, "Consulado", "L5", listOf("L4"), true),
                Estacion(83, "Eduardo Molina", "L5", emptyList(), false),
                Estacion(84, "Aragón", "L5", emptyList(), false),
                Estacion(85, "Oceanía", "L5", listOf("LB"), true),
                Estacion(86, "Terminal Aérea", "L5", emptyList(), false),
                Estacion(87, "Hangares", "L5", emptyList(), false),
                Estacion(88, "Pantitlán", "L5", listOf("L1", "L9", "LA"), true)
            )

            "LÍNEA 6" -> listOf(
                Estacion(89, "El Rosario", "L6", listOf("L7"), true),
                Estacion(90, "Tezozómoc", "L6", emptyList(), false),
                Estacion(91, "UAM-Azcapotzalco", "L6", emptyList(), false),
                Estacion(92, "Ferrería/Arena Ciudad de México", "L6", emptyList(), false),
                Estacion(93, "Norte 45", "L6", emptyList(), false),
                Estacion(94, "Vallejo", "L6", emptyList(), false),
                Estacion(95, "Instituto del Petróleo", "L6", listOf("L5"), true),
                Estacion(96, "Lindavista", "L6", emptyList(), false),
                Estacion(97, "Deportivo 18 de Marzo", "L6", listOf("L3"), true),
                Estacion(98, "La Villa-Basílica", "L6", emptyList(), false),
                Estacion(99, "Martín Carrera", "L6", listOf("L4"), true)
            )

            "LÍNEA 7" -> listOf(
                Estacion(100, "El Rosario", "L7", listOf("L6"), true),
                Estacion(101, "Aquiles Serdán", "L7", emptyList(), false),
                Estacion(102, "Camarones", "L7", emptyList(), false),
                Estacion(103, "Refinería", "L7", emptyList(), false),
                Estacion(104, "Tacuba", "L7", listOf("L2"), true),
                Estacion(105, "San Joaquín", "L7", emptyList(), false),
                Estacion(106, "Polanco", "L7", emptyList(), false),
                Estacion(107, "Auditorio", "L7", emptyList(), false),
                Estacion(108, "Constituyentes", "L7", emptyList(), false),
                Estacion(109, "Tacubaya", "L7", listOf("L1", "L9"), true),
                Estacion(110, "San Pedro de los Pinos", "L7", emptyList(), false),
                Estacion(111, "San Antonio", "L7", emptyList(), false),
                Estacion(112, "Mixcoac", "L7", listOf("L12"), true),
                Estacion(113, "Barranca del Muerto", "L7", emptyList(), false)
            )

            "LÍNEA 8" -> listOf(
                Estacion(114, "Garibaldi/Lagunilla", "L8", listOf("LB"), true),
                Estacion(115, "Bellas Artes", "L8", listOf("L2"), true),
                Estacion(116, "San Juan de Letrán", "L8", emptyList(), false),
                Estacion(117, "Salto del Agua", "L8", listOf("L1"), true),
                Estacion(118, "Doctores", "L8", emptyList(), false),
                Estacion(119, "Obrera", "L8", emptyList(), false),
                Estacion(120, "Chabacano", "L8", listOf("L2", "L9"), true),
                Estacion(121, "La Viga", "L8", emptyList(), false),
                Estacion(122, "Santa Anita", "L8", listOf("L4"), true),
                Estacion(123, "Coyuya", "L8", emptyList(), false),
                Estacion(124, "Iztacalco", "L8", emptyList(), false),
                Estacion(125, "Apatlaco", "L8", emptyList(), false),
                Estacion(126, "Aculco", "L8", emptyList(), false),
                Estacion(127, "Escuadrón 201", "L8", emptyList(), false),
                Estacion(128, "Atlalilco", "L8", listOf("L12"), true),
                Estacion(129, "Iztapalapa", "L8", emptyList(), false),
                Estacion(130, "Cerro de la Estrella", "L8", emptyList(), false),
                Estacion(131, "UAM-I", "L8", emptyList(), false),
                Estacion(132, "Constitución de 1917", "L8", emptyList(), false)
            )

            "LÍNEA 9" -> listOf(
                Estacion(133, "Tacubaya", "L9", listOf("L1", "L7"), true),
                Estacion(134, "Patriotismo", "L9", emptyList(), false),
                Estacion(135, "Chilpancingo", "L9", emptyList(), false),
                Estacion(136, "Centro Médico", "L9", listOf("L3"), true),
                Estacion(137, "Lázaro Cárdenas", "L9", emptyList(), false),
                Estacion(138, "Chabacano", "L9", listOf("L2", "L8"), true),
                Estacion(139, "Jamaica", "L9", listOf("L4"), true),
                Estacion(140, "Mixiuhca", "L9", emptyList(), false),
                Estacion(141, "Velódromo", "L9", emptyList(), false),
                Estacion(142, "Ciudad Deportiva", "L9", emptyList(), false),
                Estacion(143, "Puebla", "L9", emptyList(), false),
                Estacion(144, "Pantitlán", "L9", listOf("L1", "L5", "LA"), true)
            )

            "LÍNEA 12" -> listOf(
                Estacion(145, "Mixcoac", "L12", listOf("L7"), true),
                Estacion(146, "Insurgentes Sur", "L12", emptyList(), false),
                Estacion(147, "Hospital 20 de Noviembre", "L12", emptyList(), false),
                Estacion(148, "Zapata", "L12", listOf("L3"), true),
                Estacion(149, "Parque de los Venados", "L12", emptyList(), false),
                Estacion(150, "Eje Central", "L12", emptyList(), false),
                Estacion(151, "Ermita", "L12", listOf("L2"), true),
                Estacion(152, "Mexicaltzingo", "L12", emptyList(), false),
                Estacion(153, "Atlalilco", "L12", listOf("L8"), true),
                Estacion(154, "Culhuacán", "L12", emptyList(), false),
                Estacion(155, "San Andrés Tomatlán", "L12", emptyList(), false),
                Estacion(156, "Lomas Estrella", "L12", emptyList(), false),
                Estacion(157, "Calle 11", "L12", emptyList(), false),
                Estacion(158, "Periférico Oriente", "L12", emptyList(), false),
                Estacion(159, "Tezonco", "L12", emptyList(), false),
                Estacion(160, "Olivos", "L12", emptyList(), false),
                Estacion(161, "Nopalera", "L12", emptyList(), false),
                Estacion(162, "Zapotitlán", "L12", emptyList(), false),
                Estacion(163, "Tlaltenco", "L12", emptyList(), false),
                Estacion(164, "Tláhuac", "L12", emptyList(), false)
            )

            "LÍNEA A" -> listOf(
                Estacion(165, "Pantitlán", "LA", listOf("L1", "L5", "L9"), true),
                Estacion(166, "Agrícola Oriental", "LA", emptyList(), false),
                Estacion(167, "Canal de San Juan", "LA", emptyList(), false),
                Estacion(168, "Tepalcates", "LA", emptyList(), false),
                Estacion(169, "Guelatao", "LA", emptyList(), false),
                Estacion(170, "Peñón Viejo", "LA", emptyList(), false),
                Estacion(171, "Acatitla", "LA", emptyList(), false),
                Estacion(172, "Santa Marta", "LA", emptyList(), false),
                Estacion(173, "Los Reyes", "LA", emptyList(), false),
                Estacion(174, "La Paz", "LA", emptyList(), false)
            )

            "LÍNEA B" -> listOf(
                Estacion(175, "Ciudad Azteca", "LB", emptyList(), false),
                Estacion(176, "Plaza Aragón", "LB", emptyList(), false),
                Estacion(177, "Olímpica", "LB", emptyList(), false),
                Estacion(178, "Ecatepec", "LB", emptyList(), false),
                Estacion(179, "Múzquiz", "LB", emptyList(), false),
                Estacion(180, "Río de los Remedios", "LB", emptyList(), false),
                Estacion(181, "Impulsora", "LB", emptyList(), false),
                Estacion(182, "Nezahualcóyotl", "LB", emptyList(), false),
                Estacion(183, "Villa de Aragón", "LB", emptyList(), false),
                Estacion(184, "Bosque de Aragón", "LB", emptyList(), false),
                Estacion(185, "Deportivo Oceanía", "LB", emptyList(), false),
                Estacion(186, "Oceanía", "LB", listOf("L5"), true),
                Estacion(187, "Romero Rubio", "LB", emptyList(), false),
                Estacion(188, "Ricardo Flores Magón", "LB", emptyList(), false),
                Estacion(189, "San Lázaro", "LB", listOf("L1"), true),
                Estacion(190, "Morelos", "LB", listOf("L4"), true),
                Estacion(191, "Tepito", "LB", emptyList(), false),
                Estacion(192, "Lagunilla", "LB", emptyList(), false),
                Estacion(193, "Garibaldi/Lagunilla", "LB", listOf("L8"), true),
                Estacion(194, "Guerrero", "LB", listOf("L3"), true),
                Estacion(195, "Buenavista", "LB", emptyList(), false)
            )

            else -> emptyList()
        }
    }
}