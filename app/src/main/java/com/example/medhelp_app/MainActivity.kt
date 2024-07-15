/* Odeth Arevalo
* 20-53-7507
* Gerardo Vásquez
* 8-1002-2180
* Moisés Betancourt
* 20-70-7371
* Fernando Barrios
* 8-1002-1207*/
package com.example.medhelp_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentoAu())
                .commit()
        }
    }
}