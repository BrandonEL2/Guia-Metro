package com.example.guiametro

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Vinculamos cada botón de tu barra inferior con su respectivo ID del nav_graph.xml
        findViewById<LinearLayout>(R.id.btnTabRuta).setOnClickListener {
            navController.navigate(R.id.rutaFragment)
        }

        findViewById<LinearLayout>(R.id.btnTabEstaciones).setOnClickListener {
            navController.navigate(R.id.estacionesFragment)
        }

        findViewById<LinearLayout>(R.id.btnTabAlertas).setOnClickListener {
            // Forzamos la navegación limpia asegurándonos de que recargue el destino
            if (navController.currentDestination?.id != R.id.alertasFragment) {
                navController.navigate(R.id.alertasFragment)
            }
        }

        findViewById<LinearLayout>(R.id.btnTabMapa).setOnClickListener {
            navController.navigate(R.id.mapaFragment)
        }
    }
}