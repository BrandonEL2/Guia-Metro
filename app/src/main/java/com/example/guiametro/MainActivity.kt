package com.example.guiametro

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val options = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        // Enlazamos los clics de cada bloque vertical
        findViewById<LinearLayout>(R.id.btnTabRuta).setOnClickListener {
            navController.navigate(R.id.rutaFragment, null, options)
        }

        findViewById<LinearLayout>(R.id.btnTabEstaciones).setOnClickListener {
            navController.navigate(R.id.estacionesFragment, null, options)
        }

        findViewById<LinearLayout>(R.id.btnTabAlertas).setOnClickListener {
            navController.navigate(R.id.alertasFragment, null, options)
        }

        findViewById<LinearLayout>(R.id.btnTabMapa).setOnClickListener {
            navController.navigate(R.id.mapaFragment, null, options)
        }
    }
}