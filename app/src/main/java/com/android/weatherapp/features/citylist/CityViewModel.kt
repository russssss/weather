package com.android.weatherapp.features.citylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.weatherapp.features.citydetails.MainRepository
import com.android.weatherapp.features.dto.WetherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(private val mainRepository: MainRepository, application: Application) : AndroidViewModel(application) {

    private val weatherLiveData = MutableLiveData<List<WetherData>>()

    fun getWeather() = viewModelScope.launch(Dispatchers.Main) {
        try {
            val weather = mainRepository.getWether()
            weatherLiveData.value = weather
        } catch (e: Exception) {
        }
    }
}