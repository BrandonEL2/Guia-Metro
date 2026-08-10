package com.example.guiametro

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class ProgramarAlertaActivity : AppCompatActivity() {

    private var horaSeleccionada = 7
    private var minutoSeleccionado = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_alerta)

        val spinnerEstaciones = findViewById<AutoCompleteTextView>(R.id.spinnerEstaciones)
        val btnHora = findViewById<Button>(R.id.btnHora)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // 1. Cargar lista de estaciones (Ejemplo)
        val listaEstaciones = arrayOf("Pino Suárez", "Pantitlán", "Tacubaya", "Balderas", "Hidalgo", "Universidad")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listaEstaciones)
        spinnerEstaciones.setAdapter(adapter)

        // 2. Abrir Selector de Hora
        btnHora.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(horaSeleccionada)
                .setMinute(minutoSeleccionado)
                .setTitleText("Selecciona la hora de la alerta")
                .build()

            picker.addOnPositiveButtonClickListener {
                horaSeleccionada = picker.hour
                minutoSeleccionado = picker.minute
                val amPm = if (horaSeleccionada >= 12) "PM" else "AM"
                val hora12 = if (horaSeleccionada % 12 == 0) 12 else horaSeleccionada % 12

                btnHora.text = String.format("🕒 %02d:%02d %s", hora12, minutoSeleccionado, amPm)
            }

            picker.show(supportFragmentManager, "TIME_PICKER")
        }

        // 3. Confirmar Alerta
        btnGuardar.setOnClickListener {
            val estacion = spinnerEstaciones.text.toString()

            if (estacion.isEmpty()) {
                Toast.makeText(this, "Por favor selecciona una estación", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí activas tu WorkManager
                Toast.makeText(this, "Alerta guardada para $estacion", Toast.LENGTH_LONG).show()
                finish() // Cierra la pantalla y regresa a Alertas
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}