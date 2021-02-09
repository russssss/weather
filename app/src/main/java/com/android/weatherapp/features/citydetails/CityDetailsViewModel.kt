package com.android.weatherapp.features.citydetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.weatherapp.features.dto.WetherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityDetailsViewModel(private val mainRepository: MainRepository, application: Application) : AndroidViewModel(application) {

    private val weatherLiveData = MutableLiveData<List<WetherData>>()

    fun getWeather() = viewModelScope.launch(Dispatchers.Main) {
        try {
            val wearther = mainRepository.getWether()
            weatherLiveData.value = wearther
        } catch (e: Exception) {
        }
    }
}