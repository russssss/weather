package com.android.weatherapp.features.city

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.weather.WeatherActivity

class CityActivity : AppCompatActivity() {

    lateinit var cityViewModel: CityViewModel
    lateinit var cityAdapter: CityAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_list_layout)

        initUI()
        init()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.city_list)
        cityAdapter = CityAdapter()
        recyclerView.adapter = cityAdapter

        cityAdapter.update(ArrayList())

        cityAdapter.onItemClick = { contact ->
            startActivity(Intent(this, WeatherActivity::class.java))
        }
    }

    private fun init() {

        cityViewModel = CityViewModel(application)

        cityViewModel.weatherLiveData.observe(this, Observer {
            cityAdapter?.let { e -> cityAdapter.update(it as ArrayList<String>) }
        })

        cityViewModel.getWeather()
    }


}