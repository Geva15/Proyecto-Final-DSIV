package com.example.medhelp_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Historial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Mapeo())
                .commit()
        }
    }
}

