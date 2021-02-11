package com.android.weatherapp.features.city

import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.app.App
import com.android.weatherapp.features.app.Const
import com.android.weatherapp.features.weather.WeatherActivity
import com.android.weatherapp.features.weather.WeatherModel
import javax.inject.Inject

class CityActivity : AppCompatActivity() {

    @Inject
    lateinit var cityViewModel: CityViewModel
    lateinit var cityAdapter: CityAdapter
    private lateinit var cityInput: TextView
    private lateinit var citySubmit: TextView
    lateinit var recyclerView: RecyclerView
    val cityList = listOf("Moscow", "St. Petersburg")
    var networkCallback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            cityViewModel.getWeather(*cityList.toTypedArray())
        }

        override fun onLost(network: Network) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_list_layout)

        initUI()
        init()
        initNetwork()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.city_list)
        cityInput = findViewById(R.id.city_input)
        citySubmit = findViewById(R.id.city_submit)
        cityAdapter = CityAdapter()
        recyclerView.adapter = cityAdapter

        cityAdapter.update(ArrayList())

        cityAdapter.onItemClick = { contact ->
            startActivity(
                Intent(this, WeatherActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(
                    Const.name_city,
                    contact.city
                )
            )
        }

        citySubmit.setOnClickListener {
            cityInput.text?.toString()?.trim()?.let {
                cityInput.text = ""
                cityViewModel.getWeather(it)
            }
        }
    }

    private fun init() {
        (applicationContext as App).appComponent.inject(this)

        cityViewModel.weatherLiveData.observe(this, Observer {
            cityAdapter?.let { e -> cityAdapter.update(it as ArrayList<WeatherModel>) }
        })

        cityViewModel.getWeather(*cityList.toTypedArray())

        cityViewModel.onMessage = { e ->
            val t = Toast.makeText(applicationContext, e, Toast.LENGTH_LONG)
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show()
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