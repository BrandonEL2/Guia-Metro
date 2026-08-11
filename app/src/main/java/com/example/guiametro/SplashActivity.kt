package com.example.guiametro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el tema AQUÍ antes de dibujar la vista para evitar reinicios dobles en MainActivity
        aplicarTemaGuardado()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runnable = Runnable {
            if (!isFinishing && !isDestroyed) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        handler.postDelayed(runnable!!, 2000)
    }

    private fun aplicarTemaGuardado() {
        val prefs = getSharedPreferences("AjustesMetroPrefs", Context.MODE_PRIVATE)
        val modo = prefs.getInt("TEMA_MODO", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(modo)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela el temporizador si la pantalla se cierra antes de tiempo
        runnable?.let { handler.removeCallbacks(it) }
    }
}