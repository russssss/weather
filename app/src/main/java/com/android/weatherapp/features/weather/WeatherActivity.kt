package com.android.weatherapp.features.weather

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
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
    var networkCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                intent.getStringExtra(Const.name_city)?.let { weatherViewModel.getWeather(it) }
            }

            override fun onLost(network: Network) {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_layout)
        initUI()
        init()
        initNetwork()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.weather_list)
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

    private fun initNetwork() {
        var connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request: NetworkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }
}