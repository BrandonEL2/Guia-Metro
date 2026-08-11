package com.example.guiametro

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var btnTabRuta: LinearLayout
    private lateinit var btnTabEstaciones: LinearLayout
    private lateinit var btnTabAlertas: LinearLayout
    private lateinit var btnTabMapa: LinearLayout

    // Vistas para el encabezado dinámico
    private lateinit var txtHeaderTitulo: TextView
    private lateinit var txtHeaderSubtitulo: TextView
    private lateinit var imgHeaderIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Se removió aplicarTemaGuardado() de aquí para evitar la duplicación de Activity

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Inicializamos las vistas de los botones
        btnTabRuta = findViewById(R.id.btnTabRuta)
        btnTabEstaciones = findViewById(R.id.btnTabEstaciones)
        btnTabAlertas = findViewById(R.id.btnTabAlertas)
        btnTabMapa = findViewById(R.id.btnTabMapa)

        // Inicializamos las vistas del encabezado dinámico
        txtHeaderTitulo = findViewById(R.id.txtHeaderTitulo)
        txtHeaderSubtitulo = findViewById(R.id.txtHeaderSubtitulo)
        imgHeaderIcon = findViewById(R.id.imgHeaderIcon)

        // Configurar el listener del botón de 3 puntos
        val btnOpciones = findViewById<ImageButton>(R.id.btnOpciones)
        btnOpciones.setOnClickListener { view ->
            mostrarMenuOpciones(view)
        }

        // Escucha los cambios de pantalla para actualizar las pestañas y el encabezado
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.rutaFragment -> {
                    actualizarEstadoTabs(btnTabRuta)
                    imgHeaderIcon.visibility = View.GONE
                    txtHeaderTitulo.text = "Planifica tu ruta"
                    txtHeaderSubtitulo.text = "Selecciona un origen y un destino"
                }
                R.id.estacionesFragment -> {
                    actualizarEstadoTabs(btnTabEstaciones)
                    imgHeaderIcon.visibility = View.GONE
                    txtHeaderTitulo.text = "🚇 MetroGuía CDMX"
                    txtHeaderSubtitulo.text = "Explora líneas, estaciones y encuentra tu ruta ideal"
                }
                R.id.alertasFragment -> {
                    actualizarEstadoTabs(btnTabAlertas)
                    imgHeaderIcon.visibility = View.GONE
                    txtHeaderTitulo.text = "Alertas y Avisos"
                    txtHeaderSubtitulo.text = "Estado del servicio en tiempo real"
                }
                R.id.mapaFragment -> {
                    actualizarEstadoTabs(btnTabMapa)
                    imgHeaderIcon.visibility = View.GONE
                    txtHeaderTitulo.text = "Mapa de la Red"
                    txtHeaderSubtitulo.text = "Visualiza el mapa de líneas"
                }
            }
        }

        // Navegación con verificación para no recargar la pantalla si ya estás en ella
        btnTabRuta.setOnClickListener {
            if (navController.currentDestination?.id != R.id.rutaFragment) {
                navController.navigate(R.id.rutaFragment)
            }
        }

        btnTabEstaciones.setOnClickListener {
            if (navController.currentDestination?.id != R.id.estacionesFragment) {
                navController.navigate(R.id.estacionesFragment)
            }
        }

        btnTabAlertas.setOnClickListener {
            if (navController.currentDestination?.id != R.id.alertasFragment) {
                navController.navigate(R.id.alertasFragment)
            }
        }

        btnTabMapa.setOnClickListener {
            if (navController.currentDestination?.id != R.id.mapaFragment) {
                navController.navigate(R.id.mapaFragment)
            }
        }
    }

    // Despliega el menú de 3 puntos flotante (PopupMenu) al presionar el icono
    private fun mostrarMenuOpciones(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.main_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tema_claro -> {
                    cambiarTema(AppCompatDelegate.MODE_NIGHT_NO)
                    true
                }
                R.id.tema_oscuro -> {
                    cambiarTema(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    // Guarda y aplica el tema seleccionado cuando el usuario lo cambia manualmente
    private fun cambiarTema(modo: Int) {
        AppCompatDelegate.setDefaultNightMode(modo)
        val prefs = getSharedPreferences("AjustesMetroPrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("TEMA_MODO", modo).apply()
    }

    // Cambia la propiedad isSelected del botón y sus vistas hijas (ImageView y TextView)
    private fun actualizarEstadoTabs(tabSeleccionado: View) {
        val tabs = listOf(btnTabRuta, btnTabEstaciones, btnTabAlertas, btnTabMapa)

        tabs.forEach { tab ->
            val esElSeleccionado = (tab == tabSeleccionado)

            tab.isSelected = esElSeleccionado
            for (i in 0 until tab.childCount) {
                tab.getChildAt(i).isSelected = esElSeleccionado
            }
        }
    }
}