package com.android.weatherapp.features.citydetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.citylist.CityViewModel

class CityDetailsActivity : AppCompatActivity() {

    lateinit var cityViewModel: CityViewModel
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
            startActivity(Intent(this, CityDetailsActivity::class.java))
        }
    }

    private fun init() {
        supportActionBar?.setHomeButtonEnabled(true)

        val mock = mutableListOf<String>("+8", "+9", "+12", "+15", "+20", "+19")

        weatherAdapter.update(mock as ArrayList<String>)
    }
}