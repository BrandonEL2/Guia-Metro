package com.example.guiametro.repository

import com.example.guiametro.*
import com.example.guiametro.graph.GrafoMetro

class MetroRepository {

    val grafo = GrafoMetro()

    // Definición de las 195 Estaciones de la Red
    // LÍNEA 1
    val e1 = Estacion(1, "Observatorio", "L1", listOf("L12"), true)
    val e2 = Estacion(2, "Tacubaya", "L1", listOf("L7", "L9"), true)
    val e3 = Estacion(3, "Juanacatlán", "L1")
    val e4 = Estacion(4, "Chapultepec", "L1")
    val e5 = Estacion(5, "Sevilla", "L1")
    val e6 = Estacion(6, "Insurgentes", "L1")
    val e7 = Estacion(7, "Cuauhtémoc", "L1")
    val e8 = Estacion(8, "Balderas", "L1", listOf("L3"), true)
    val e9 = Estacion(9, "Salto del Agua", "L1", listOf("L8"), true)
    val e10 = Estacion(10, "Isabel la Católica", "L1")
    val e11 = Estacion(11, "Pino Suárez", "L1", listOf("L2"), true)
    val e12 = Estacion(12, "Merced", "L1")
    val e13 = Estacion(13, "Candelaria", "L1", listOf("L4"), true)
    val e14 = Estacion(14, "San Lázaro", "L1", listOf("LB"), true)
    val e15 = Estacion(15, "Moctezuma", "L1")
    val e16 = Estacion(16, "Balbuena", "L1")
    val e17 = Estacion(17, "Boulevard Puerto Aéreo", "L1")
    val e18 = Estacion(18, "Gómez Farías", "L1")
    val e19 = Estacion(19, "Zaragoza", "L1")
    val e20 = Estacion(20, "Pantitlán", "L1", listOf("L5", "L9", "LA"), true)

    // LÍNEA 2
    val e21 = Estacion(21, "Cuatro Caminos", "L2")
    val e22 = Estacion(22, "Panteones", "L2")
    val e23 = Estacion(23, "Tacuba", "L2", listOf("L7"), true)
    val e24 = Estacion(24, "Cuitláhuac", "L2")
    val e25 = Estacion(25, "Popotla", "L2")
    val e26 = Estacion(26, "Colegio Militar", "L2")
    val e27 = Estacion(27, "Normal", "L2")
    val e28 = Estacion(28, "San Cosme", "L2")
    val e29 = Estacion(29, "Revolución", "L2")
    val e30 = Estacion(30, "Hidalgo", "L2", listOf("L3"), true)
    val e31 = Estacion(31, "Bellas Artes", "L2", listOf("L8"), true)
    val e32 = Estacion(32, "Allende", "L2")
    val e33 = Estacion(33, "Zócalo/Tenochtitlan", "L2")
    val e34 = Estacion(34, "Pino Suárez", "L2", listOf("L1"), true)
    val e35 = Estacion(35, "San Antonio Abad", "L2")
    val e36 = Estacion(36, "Chabacano", "L2", listOf("L8", "L9"), true)
    val e37 = Estacion(37, "Viaducto", "L2")
    val e38 = Estacion(38, "Xola", "L2")
    val e39 = Estacion(39, "Villa de Cortés", "L2")
    val e40 = Estacion(40, "Nativitas", "L2")
    val e41 = Estacion(41, "Portales", "L2")
    val e42 = Estacion(42, "Ermita", "L2", listOf("L12"), true)
    val e43 = Estacion(43, "General Anaya", "L2")
    val e44 = Estacion(44, "Tasqueña", "L2")

    // LÍNEA 3
    val e45 = Estacion(45, "Indios Verdes", "L3")
    val e46 = Estacion(46, "Deportivo 18 de Marzo", "L3", listOf("L6"), true)
    val e47 = Estacion(47, "Potrero", "L3")
    val e48 = Estacion(48, "La Raza", "L3", listOf("L5"), true)
    val e49 = Estacion(49, "Tlatelolco", "L3")
    val e50 = Estacion(50, "Guerrero", "L3", listOf("LB"), true)
    val e51 = Estacion(51, "Hidalgo", "L3", listOf("L2"), true)
    val e52 = Estacion(52, "Juárez", "L3")
    val e53 = Estacion(53, "Balderas", "L3", listOf("L1"), true)
    val e54 = Estacion(54, "Niños Héroes", "L3")
    val e55 = Estacion(55, "Hospital General", "L3")
    val e56 = Estacion(56, "Centro Médico", "L3", listOf("L9"), true)
    val e57 = Estacion(57, "Etiopía/Plaza de la Transparencia", "L3")
    val e58 = Estacion(58, "Eugenia", "L3")
    val e59 = Estacion(59, "División del Norte", "L3")
    val e60 = Estacion(60, "Zapata", "L3", listOf("L12"), true)
    val e61 = Estacion(61, "Coyoacán", "L3")
    val e62 = Estacion(62, "Viveros/Derechos Humanos", "L3")
    val e63 = Estacion(63, "Miguel Ángel de Quevedo", "L3")
    val e64 = Estacion(64, "Copilco", "L3")
    val e65 = Estacion(65, "Universidad", "L3")

    // LÍNEA 4
    val e66 = Estacion(66, "Martín Carrera", "L4", listOf("L6"), true)
    val e67 = Estacion(67, "Talismán", "L4")
    val e68 = Estacion(68, "Bondojito", "L4")
    val e69 = Estacion(69, "Consulado", "L4", listOf("L5"), true)
    val e70 = Estacion(70, "Canal del Norte", "L4")
    val e71 = Estacion(71, "Morelos", "L4", listOf("LB"), true)
    val e72 = Estacion(72, "Candelaria", "L4", listOf("L1"), true)
    val e73 = Estacion(73, "Fray Servando", "L4")
    val e74 = Estacion(74, "Jamaica", "L4", listOf("L9"), true)
    val e75 = Estacion(75, "Santa Anita", "L4", listOf("L8"), true)

    // LÍNEA 5
    val e76 = Estacion(76, "Politécnico", "L5")
    val e77 = Estacion(77, "Instituto del Petróleo", "L5", listOf("L6"), true)
    val e78 = Estacion(78, "Autobuses del Norte", "L5")
    val e79 = Estacion(79, "La Raza", "L5", listOf("L3"), true)
    val e80 = Estacion(80, "Misterios", "L5")
    val e81 = Estacion(81, "Valle Gómez", "L5")
    val e82 = Estacion(82, "Consulado", "L5", listOf("L4"), true)
    val e83 = Estacion(83, "Eduardo Molina", "L5")
    val e84 = Estacion(84, "Aragón", "L5")
    val e85 = Estacion(85, "Oceanía", "L5", listOf("LB"), true)
    val e86 = Estacion(86, "Terminal Aérea", "L5")
    val e87 = Estacion(87, "Hangares", "L5")
    val e88 = Estacion(88, "Pantitlán", "L5", listOf("L1", "L9", "LA"), true)

    // LÍNEA 6
    val e89 = Estacion(89, "El Rosario", "L6", listOf("L7"), true)
    val e90 = Estacion(90, "Tezozómoc", "L6")
    val e91 = Estacion(91, "UAM-Azcapotzalco", "L6")
    val e92 = Estacion(92, "Ferrería/Arena Ciudad de México", "L6")
    val e93 = Estacion(93, "Norte 45", "L6")
    val e94 = Estacion(94, "Vallejo", "L6")
    val e95 = Estacion(95, "Instituto del Petróleo", "L6", listOf("L5"), true)
    val e96 = Estacion(96, "Lindavista", "L6")
    val e97 = Estacion(97, "Deportivo 18 de Marzo", "L6", listOf("L3"), true)
    val e98 = Estacion(98, "La Villa-Basílica", "L6")
    val e99 = Estacion(99, "Martín Carrera", "L6", listOf("L4"), true)

    // LÍNEA 7
    val e100 = Estacion(100, "El Rosario", "L7", listOf("L6"), true)
    val e101 = Estacion(101, "Aquiles Serdán", "L7")
    val e102 = Estacion(102, "Camarones", "L7")
    val e103 = Estacion(103, "Refinería", "L7")
    val e104 = Estacion(104, "Tacuba", "L7", listOf("L2"), true)
    val e105 = Estacion(105, "San Joaquín", "L7")
    val e106 = Estacion(106, "Polanco", "L7")
    val e107 = Estacion(107, "Auditorio", "L7")
    val e108 = Estacion(108, "Constituyentes", "L7")
    val e109 = Estacion(109, "Tacubaya", "L7", listOf("L1", "L9"), true)
    val e110 = Estacion(110, "San Pedro de los Pinos", "L7")
    val e111 = Estacion(111, "San Antonio", "L7")
    val e112 = Estacion(112, "Mixcoac", "L7", listOf("L12"), true)
    val e113 = Estacion(113, "Barranca del Muerto", "L7")

    // LÍNEA 8
    val e114 = Estacion(114, "Garibaldi/Lagunilla", "L8", listOf("LB"), true)
    val e115 = Estacion(115, "Bellas Artes", "L8", listOf("L2"), true)
    val e116 = Estacion(116, "San Juan de Letrán", "L8")
    val e117 = Estacion(117, "Salto del Agua", "L8", listOf("L1"), true)
    val e118 = Estacion(118, "Doctores", "L8")
    val e119 = Estacion(119, "Obrera", "L8")
    val e120 = Estacion(120, "Chabacano", "L8", listOf("L2", "L9"), true)
    val e121 = Estacion(121, "La Viga", "L8")
    val e122 = Estacion(122, "Santa Anita", "L8", listOf("L4"), true)
    val e123 = Estacion(123, "Coyuya", "L8")
    val e124 = Estacion(124, "Iztacalco", "L8")
    val e125 = Estacion(125, "Apatlaco", "L8")
    val e126 = Estacion(126, "Aculco", "L8")
    val e127 = Estacion(127, "Escuadrón 201", "L8")
    val e128 = Estacion(128, "Atlalilco", "L8", listOf("L12"), true)
    val e129 = Estacion(129, "Iztapalapa", "L8")
    val e130 = Estacion(130, "Cerro de la Estrella", "L8")
    val e131 = Estacion(131, "UAM-I", "L8")
    val e132 = Estacion(132, "Constitución de 1917", "L8")

    // LÍNEA 9
    val e133 = Estacion(133, "Tacubaya", "L9", listOf("L1", "L7"), true)
    val e134 = Estacion(134, "Patriotismo", "L9")
    val e135 = Estacion(135, "Chilpancingo", "L9")
    val e136 = Estacion(136, "Centro Médico", "L9", listOf("L3"), true)
    val e137 = Estacion(137, "Lázaro Cárdenas", "L9")
    val e138 = Estacion(138, "Chabacano", "L9", listOf("L2", "L8"), true)
    val e139 = Estacion(139, "Jamaica", "L9", listOf("L4"), true)
    val e140 = Estacion(140, "Mixiuhca", "L9")
    val e141 = Estacion(141, "Velódromo", "L9")
    val e142 = Estacion(142, "Ciudad Deportiva", "L9")
    val e143 = Estacion(143, "Puebla", "L9")
    val e144 = Estacion(144, "Pantitlán", "L9", listOf("L1", "L5", "LA"), true)

    // LÍNEA 12
    val e145 = Estacion(145, "Mixcoac", "L12", listOf("L7"), true)
    val e146 = Estacion(146, "Insurgentes Sur", "L12")
    val e147 = Estacion(147, "Hospital 20 de Noviembre", "L12")
    val e148 = Estacion(148, "Zapata", "L12", listOf("L3"), true)
    val e149 = Estacion(149, "Parque de los Venados", "L12")
    val e150 = Estacion(150, "Eje Central", "L12")
    val e151 = Estacion(151, "Ermita", "L12", listOf("L2"), true)
    val e152 = Estacion(152, "Mexicaltzingo", "L12")
    val e153 = Estacion(153, "Atlalilco", "L12", listOf("L8"), true)
    val e154 = Estacion(154, "Culhuacán", "L12")
    val e155 = Estacion(155, "San Andrés Tomatlán", "L12")
    val e156 = Estacion(156, "Lomas Estrella", "L12")
    val e157 = Estacion(157, "Calle 11", "L12")
    val e158 = Estacion(158, "Periférico Oriente", "L12")
    val e159 = Estacion(159, "Tezonco", "L12")
    val e160 = Estacion(160, "Olivos", "L12")
    val e161 = Estacion(161, "Nopalera", "L12")
    val e162 = Estacion(162, "Zapotitlán", "L12")
    val e163 = Estacion(163, "Tlaltenco", "L12")
    val e164 = Estacion(164, "Tláhuac", "L12")

    // LÍNEA A
    val e165 = Estacion(165, "Pantitlán", "LA", listOf("L1", "L5", "L9"), true)
    val e166 = Estacion(166, "Agrícola Oriental", "LA")
    val e167 = Estacion(167, "Canal de San Juan", "LA")
    val e168 = Estacion(168, "Tepalcates", "LA")
    val e169 = Estacion(169, "Guelatao", "LA")
    val e170 = Estacion(170, "Peñón Viejo", "LA")
    val e171 = Estacion(171, "Acatitla", "LA")
    val e172 = Estacion(172, "Santa Marta", "LA")
    val e173 = Estacion(173, "Los Reyes", "LA")
    val e174 = Estacion(174, "La Paz", "LA")

    // LÍNEA B
    val e175 = Estacion(175, "Ciudad Azteca", "LB")
    val e176 = Estacion(176, "Plaza Aragón", "LB")
    val e177 = Estacion(177, "Olímpica", "LB")
    val e178 = Estacion(178, "Ecatepec", "LB")
    val e179 = Estacion(179, "Múzquiz", "LB")
    val e180 = Estacion(180, "Río de los Remedios", "LB")
    val e181 = Estacion(181, "Impulsora", "LB")
    val e182 = Estacion(182, "Nezahualcóyotl", "LB")
    val e183 = Estacion(183, "Villa de Aragón", "LB")
    val e184 = Estacion(184, "Bosque de Aragón", "LB")
    val e185 = Estacion(185, "Deportivo Oceanía", "LB")
    val e186 = Estacion(186, "Oceanía", "LB", listOf("L5"), true)
    val e187 = Estacion(187, "Romero Rubio", "LB")
    val e188 = Estacion(188, "Ricardo Flores Magón", "LB")
    val e189 = Estacion(189, "San Lázaro", "LB", listOf("L1"), true)
    val e190 = Estacion(190, "Morelos", "LB", listOf("L4"), true)
    val e191 = Estacion(191, "Tepito", "LB")
    val e192 = Estacion(192, "Lagunilla", "LB")
    val e193 = Estacion(193, "Garibaldi/Lagunilla", "LB", listOf("L8"), true)
    val e194 = Estacion(194, "Guerrero", "LB", listOf("L3"), true)
    val e195 = Estacion(195, "Buenavista", "LB")

    init {
        cargarLineas()
    }

    private fun cargarLineas() {
        val estaciones = listOf(
            e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20,
            e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31, e32, e33, e34, e35, e36, e37, e38, e39, e40, e41, e42, e43, e44,
            e45, e46, e47, e48, e49, e50, e51, e52, e53, e54, e55, e56, e57, e58, e59, e60, e61, e62, e63, e64, e65,
            e66, e67, e68, e69, e70, e71, e72, e73, e74, e75,
            e76, e77, e78, e79, e80, e81, e82, e83, e84, e85, e86, e87, e88,
            e89, e90, e91, e92, e93, e94, e95, e96, e97, e98, e99,
            e100, e101, e102, e103, e104, e105, e106, e107, e108, e109, e110, e111, e112, e113,
            e114, e115, e116, e117, e118, e119, e120, e121, e122, e123, e124, e125, e126, e127, e128, e129, e130, e131, e132,
            e133, e134, e135, e136, e137, e138, e139, e140, e141, e142, e143, e144,
            e145, e146, e147, e148, e149, e150, e151, e152, e153, e154, e155, e156, e157, e158, e159, e160, e161, e162, e163, e164,
            e165, e166, e167, e168, e169, e170, e171, e172, e173, e174,
            e175, e176, e177, e178, e179, e180, e181, e182, e183, e184, e185, e186, e187, e188, e189, e190, e191, e192, e193, e194, e195
        )

        // Registrar nodos en el grafo
        estaciones.forEach { grafo.agregarEstacion(it) }

        // Conexiones por Línea (Peso por defecto = 2 minutos)
        conectarTramo(listOf(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20))
        conectarTramo(listOf(e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31, e32, e33, e34, e35, e36, e37, e38, e39, e40, e41, e42, e43, e44))
        conectarTramo(listOf(e45, e46, e47, e48, e49, e50, e51, e52, e53, e54, e55, e56, e57, e58, e59, e60, e61, e62, e63, e64, e65))
        conectarTramo(listOf(e66, e67, e68, e69, e70, e71, e72, e73, e74, e75))
        conectarTramo(listOf(e76, e77, e78, e79, e80, e81, e82, e83, e84, e85, e86, e87, e88))
        conectarTramo(listOf(e89, e90, e91, e92, e93, e94, e95, e96, e97, e98, e99))
        conectarTramo(listOf(e100, e101, e102, e103, e104, e105, e106, e107, e108, e109, e110, e111, e112, e113))
        conectarTramo(listOf(e114, e115, e116, e117, e118, e119, e120, e121, e122, e123, e124, e125, e126, e127, e128, e129, e130, e131, e132))
        conectarTramo(listOf(e133, e134, e135, e136, e137, e138, e139, e140, e141, e142, e143, e144))
        conectarTramo(listOf(e145, e146, e147, e148, e149, e150, e151, e152, e153, e154, e155, e156, e157, e158, e159, e160, e161, e162, e163, e164))
        conectarTramo(listOf(e165, e166, e167, e168, e169, e170, e171, e172, e173, e174))
        conectarTramo(listOf(e175, e176, e177, e178, e179, e180, e181, e182, e183, e184, e185, e186, e187, e188, e189, e190, e191, e192, e193, e194, e195))

        // Conexiones de Transbordos entre correspondencias (Peso = 5 minutos aprox por transbordo)
        conectarTransbordo(e2, e109)   // Tacubaya L1 <-> L7
        conectarTransbordo(e2, e133)   // Tacubaya L1 <-> L9
        conectarTransbordo(e109, e133) // Tacubaya L7 <-> L9
        conectarTransbordo(e8, e53)    // Balderas L1 <-> L3
        conectarTransbordo(e9, e117)   // Salto del Agua L1 <-> L8
        conectarTransbordo(e11, e34)   // Pino Suárez L1 <-> L2
        conectarTransbordo(e13, e72)   // Candelaria L1 <-> L4
        conectarTransbordo(e14, e189)  // San Lázaro L1 <-> LB
        conectarTransbordo(e20, e88)   // Pantitlán L1 <-> L5
        conectarTransbordo(e20, e144)  // Pantitlán L1 <-> L9
        conectarTransbordo(e20, e165)  // Pantitlán L1 <-> LA
        conectarTransbordo(e23, e104)  // Tacuba L2 <-> L7
        conectarTransbordo(e30, e51)   // Hidalgo L2 <-> L3
        conectarTransbordo(e31, e115)  // Bellas Artes L2 <-> L8
        conectarTransbordo(e36, e120)  // Chabacano L2 <-> L8
        conectarTransbordo(e36, e138)  // Chabacano L2 <-> L9
        conectarTransbordo(e120, e138) // Chabacano L8 <-> L9
        conectarTransbordo(e42, e151)  // Ermita L2 <-> L12
        conectarTransbordo(e46, e97)   // Dep. 18 de Marzo L3 <-> L6
        conectarTransbordo(e48, e79)   // La Raza L3 <-> L5
        conectarTransbordo(e50, e194)  // Guerrero L3 <-> LB
        conectarTransbordo(e56, e136)  // Centro Médico L3 <-> L9
        conectarTransbordo(e60, e148)  // Zapata L3 <-> L12
        conectarTransbordo(e66, e99)   // Martín Carrera L4 <-> L6
        conectarTransbordo(e69, e82)   // Consulado L4 <-> L5
        conectarTransbordo(e71, e190)  // Morelos L4 <-> LB
        conectarTransbordo(e74, e139)  // Jamaica L4 <-> L9
        conectarTransbordo(e75, e122)  // Santa Anita L4 <-> L8
        conectarTransbordo(e77, e95)   // Inst. del Petróleo L5 <-> L6
        conectarTransbordo(e85, e186)  // Oceanía L5 <-> LB
        conectarTransbordo(e89, e100)  // El Rosario L6 <-> L7
        conectarTransbordo(e112, e145) // Mixcoac L7 <-> L12
        conectarTransbordo(e114, e193) // Garibaldi L8 <-> LB
        conectarTransbordo(e128, e153) // Atlalilco L8 <-> L12
    }

    private fun conectarTramo(listaEstaciones: List<Estacion>, tiempoEntreEstaciones: Int = 2) {
        for (i in 0 until listaEstaciones.size - 1) {
            // El método .conectar de tu GrafoMetro ya maneja ambos sentidos (ida y vuelta)
            grafo.conectar(listaEstaciones[i].id, listaEstaciones[i + 1].id, tiempoEntreEstaciones, transbordo = false)
        }
    }

    private fun conectarTransbordo(e1: Estacion, e2: Estacion, tiempoTransbordo: Int = 5) {
        // Aquí indicamos explícitamente que es un transbordo (transbordo = true)
        grafo.conectar(e1.id, e2.id, tiempoTransbordo, transbordo = true)
    }

    fun obtenerEstacionesPorLinea(): Map<String, List<Estacion>> {
        return mapOf(
            "LÍNEA 1" to listOf(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20),
            "LÍNEA 2" to listOf(e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31, e32, e33, e34, e35, e36, e37, e38, e39, e40, e41, e42, e43, e44),
            "LÍNEA 3" to listOf(e45, e46, e47, e48, e49, e50, e51, e52, e53, e54, e55, e56, e57, e58, e59, e60, e61, e62, e63, e64, e65),
            "LÍNEA 4" to listOf(e66, e67, e68, e69, e70, e71, e72, e73, e74, e75),
            "LÍNEA 5" to listOf(e76, e77, e78, e79, e80, e81, e82, e83, e84, e85, e86, e87, e88),
            "LÍNEA 6" to listOf(e89, e90, e91, e92, e93, e94, e95, e96, e97, e98, e99),
            "LÍNEA 7" to listOf(e100, e101, e102, e103, e104, e105, e106, e107, e108, e109, e110, e111, e112, e113),
            "LÍNEA 8" to listOf(e114, e115, e116, e117, e118, e119, e120, e121, e122, e123, e124, e125, e126, e127, e128, e129, e130, e131, e132),
            "LÍNEA 9" to listOf(e133, e134, e135, e136, e137, e138, e139, e140, e141, e142, e143, e144),
            "LÍNEA 12" to listOf(e145, e146, e147, e148, e149, e150, e151, e152, e153, e154, e155, e156, e157, e158, e159, e160, e161, e162, e163, e164),
            "LÍNEA A" to listOf(e165, e166, e167, e168, e169, e170, e171, e172, e173, e174),
            "LÍNEA B" to listOf(e175, e176, e177, e178, e179, e180, e181, e182, e183, e184, e185, e186, e187, e188, e189, e190, e191, e192, e193, e194, e195)
        )
    }
}