package com.android.weatherapp.features.weather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.app.App
import com.android.weatherapp.features.app.Const
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
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
        (applicationContext as App).appComponent.inject(this)

        supportActionBar?.setHomeButtonEnabled(true)

        weatherViewModel.weatherLiveData.observe(this, Observer {
            weatherAdapter?.let { e -> weatherAdapter.update((it as WeatherModel).weather as ArrayList<Weather>) }
        })
        intent.getStringExtra(Const.name_city)?.let {
            supportActionBar?.setTitle(it)
            weatherViewModel.getWeather(it)
        }
    }
}