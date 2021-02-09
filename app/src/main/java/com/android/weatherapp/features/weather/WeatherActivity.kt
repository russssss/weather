package com.android.weatherapp.features.weather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R

class WeatherActivity : AppCompatActivity() {

    lateinit var weatherViewModel: WeatherViewModel
    lateinit var weatherAdapter: WeatherAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_list_layout)
        initUI()
        init()
    }

    private fun initUI() {

        recyclerView = findViewById(R.id.city_list)
        weatherAdapter = WeatherAdapter()
        recyclerView.adapter = weatherAdapter

        weatherAdapter.onItemClick = { contact ->
            startActivity(Intent(this, WeatherActivity::class.java))
        }
    }

    private fun init() {
        supportActionBar?.setHomeButtonEnabled(true)

        weatherViewModel = WeatherViewModel(application)

        weatherViewModel.weatherLiveData.observe(this, Observer {
            weatherAdapter?.let { e -> weatherAdapter.update(it as ArrayList<String>) }
        })

        weatherViewModel.getWeather()
    }
}