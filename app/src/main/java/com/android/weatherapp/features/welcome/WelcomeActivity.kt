package com.android.weatherapp.features.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.weatherapp.R
import com.android.weatherapp.features.city.CityActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.submit).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CityActivity::class.java
                ))
        }

    }
}