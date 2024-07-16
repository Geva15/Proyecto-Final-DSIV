package com.example.medhelp_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Inicio : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        auth = FirebaseAuth.getInstance()

        val bienvenida = findViewById<TextView>(R.id.bienvenida)
        val botonDetectar = findViewById<Button>(R.id.detectar)
        val botonHistorial = findViewById<Button>(R.id.historial)

        // Obtener el usuario actual
        val user = auth.currentUser
        user?.let {
            val displayName = it.displayName ?: "Usuario"
            bienvenida.text = "¡Bienvenido $displayName!"
        }

        // Manejar botón "Detectar Medicamento"
        botonDetectar.setOnClickListener {
            val intent = Intent(this, Detectar::class.java)
            startActivity(intent)
        }

        // Manejar botón "Historial"
        botonHistorial.setOnClickListener {
            val intent = Intent(this, Historial::class.java)
            startActivity(intent)
        }
    }
}
