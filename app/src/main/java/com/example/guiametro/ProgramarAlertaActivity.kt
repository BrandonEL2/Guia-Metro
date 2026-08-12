package com.example.guiametro

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class ProgramarAlertaActivity : AppCompatActivity() {

    private var horaSeleccionada = 7
    private var minutoSeleccionado = 30
    private var textoHoraFormateada = "07:30 AM"

    private lateinit var contenedorAlertas: LinearLayout
    private lateinit var txtTituloAlertas: TextView
    private lateinit var spinnerLineas: AutoCompleteTextView
    private lateinit var btnHora: Button

    // Contrato moderno para solicitar el permiso sin reiniciar ni cerrar la Activity
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No recibirás alertas hasta habilitar los permisos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_alerta)

        spinnerLineas = findViewById(R.id.spinnerLineas)
        btnHora = findViewById(R.id.btnHora)
        contenedorAlertas = findViewById(R.id.contenedorAlertas)
        txtTituloAlertas = findViewById(R.id.txtTituloAlertas)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Solicitar el permiso de forma segura al abrir la pantalla
        solicitarPermisoNotificaciones()

        val listaLineas = arrayOf(
            "Línea 1", "Línea 2", "Línea 3", "Línea 4",
            "Línea 5", "Línea 6", "Línea 7", "Línea 8",
            "Línea 9", "Línea 12", "Línea A", "Línea B"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listaLineas)
        spinnerLineas.setAdapter(adapter)

        renderizarListaAlertas()

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

                textoHoraFormateada = String.format("%02d:%02d %s", hora12, minutoSeleccionado, amPm)
                btnHora.text = "🕒 $textoHoraFormateada"
            }

            picker.show(supportFragmentManager, "TIME_PICKER")
        }

        btnGuardar.setOnClickListener {
            val linea = spinnerLineas.text.toString()

            if (linea.isEmpty()) {
                Toast.makeText(this, "Por favor selecciona una línea", Toast.LENGTH_SHORT).show()
            } else {
                val alertId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

                guardarAlertaEnPrefs(alertId, linea, textoHoraFormateada, horaSeleccionada, minutoSeleccionado)
                programarNotificacionDiaria(alertId, linea, horaSeleccionada, minutoSeleccionado)

                renderizarListaAlertas()
                Toast.makeText(this, "¡Alerta agregada!", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener { finish() }
    }

    private fun renderizarListaAlertas() {
        contenedorAlertas.removeAllViews()
        val prefs = getSharedPreferences("AlertasMetroPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("LISTA_ALERTAS_JSON", "[]"))

        if (jsonArray.length() > 0) {
            txtTituloAlertas.visibility = View.VISIBLE
        } else {
            txtTituloAlertas.visibility = View.GONE
        }

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val alertId = obj.getInt("id")
            val linea = obj.getString("linea")
            val horaText = obj.getString("horaText")

            val cardItem = layoutInflater.inflate(R.layout.item_alerta_guardada, contenedorAlertas, false)

            val txtLinea = cardItem.findViewById<TextView>(R.id.itemTxtLinea)
            val txtHora = cardItem.findViewById<TextView>(R.id.itemTxtHora)
            val btnEliminar = cardItem.findViewById<Button>(R.id.itemBtnEliminar)

            txtLinea.text = "📌 Línea seleccionada: $linea"
            txtHora.text = "Hora de notificación: $horaText"

            btnEliminar.setOnClickListener {
                eliminarAlertaDePrefs(alertId)
                cancelarNotificacion(alertId)
                renderizarListaAlertas()
                Toast.makeText(this, "Alerta eliminada", Toast.LENGTH_SHORT).show()
            }

            contenedorAlertas.addView(cardItem)
        }
    }

    private fun guardarAlertaEnPrefs(id: Int, linea: String, horaText: String, hora: Int, minuto: Int) {
        val prefs = getSharedPreferences("AlertasMetroPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("LISTA_ALERTAS_JSON", "[]"))

        val nuevaAlerta = JSONObject().apply {
            put("id", id)
            put("linea", linea)
            put("horaText", horaText)
            put("hora", hora)
            put("minuto", minuto)
        }

        jsonArray.put(nuevaAlerta)
        prefs.edit().putString("LISTA_ALERTAS_JSON", jsonArray.toString()).apply()
    }

    private fun eliminarAlertaDePrefs(id: Int) {
        val prefs = getSharedPreferences("AlertasMetroPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("LISTA_ALERTAS_JSON", "[]"))
        val nuevoArray = JSONArray()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            if (obj.getInt("id") != id) {
                nuevoArray.put(obj)
            }
        }

        prefs.edit().putString("LISTA_ALERTAS_JSON", nuevoArray.toString()).apply()
    }

    private fun programarNotificacionDiaria(id: Int, linea: String, hora: Int, minuto: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertaReceiver::class.java).apply {
            putExtra("GUARDADA_LINEA", linea)
            putExtra("ALERT_ID", id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hora)
            set(Calendar.MINUTE, minuto)
            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelarNotificacion(id: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertaReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}