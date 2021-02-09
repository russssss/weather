package com.android.weatherapp.features.city

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(application: Application) :
    AndroidViewModel(application) {

    private val mock = mutableListOf<String>("Moscow", "Spb")
    internal val weatherLiveData = MutableLiveData<List<String>>()

    fun getWeather() = viewModelScope.launch(Dispatchers.Main) {
//        try {
//            val weather = mainRepository.getWether()
//            weatherLiveData.value = weather
//        } catch (e: Exception) {
//        }

        try {
            weatherLiveData.value = mock
        } catch (e: Exception) {

        }

    }
}