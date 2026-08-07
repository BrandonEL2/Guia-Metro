package com.example.guiametro

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var btnTabRuta: LinearLayout
    private lateinit var btnTabEstaciones: LinearLayout
    private lateinit var btnTabAlertas: LinearLayout
    private lateinit var btnTabMapa: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
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

        // Escucha los cambios de pantalla para iluminar el botón correcto (incluso al presionar "Atrás")
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.rutaFragment -> actualizarEstadoTabs(btnTabRuta)
                R.id.estacionesFragment -> actualizarEstadoTabs(btnTabEstaciones)
                R.id.alertasFragment -> actualizarEstadoTabs(btnTabAlertas)
                R.id.mapaFragment -> actualizarEstadoTabs(btnTabMapa)
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