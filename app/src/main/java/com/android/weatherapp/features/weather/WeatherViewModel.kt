package com.android.weatherapp.features.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val mock = mutableListOf<String>("+7", "+8")
    internal val weatherLiveData = MutableLiveData<List<String>>()

    fun getWeather() = viewModelScope.launch(Dispatchers.Main) {
//        try {
//            val wearther = mainRepository.getWether()
//            weatherLiveData.value = wearther
//        } catch (e: Exception) {
//        }

        try {
            weatherLiveData.value = mock
        } catch (e: Exception) {

        }
    }
}