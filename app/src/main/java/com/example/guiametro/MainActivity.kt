package com.example.guiametro

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Botón Ruta
        findViewById<Button>(R.id.btnTabRuta).setOnClickListener {
            navController.navigate(R.id.rutaFragment)
        }

        // Botón Itinerario
        findViewById<Button>(R.id.btnTabItinerario).setOnClickListener {
            navController.navigate(R.id.itinerarioFragment)
        }

        // Botón Estaciones
        findViewById<Button>(R.id.btnTabEstaciones).setOnClickListener {
            navController.navigate(R.id.estacionesFragment)
        }

        // Botón Alertas
        findViewById<Button>(R.id.btnTabAlertas).setOnClickListener {
            navController.navigate(R.id.alertasFragment)
        }
    }
}