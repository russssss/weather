package com.android.weatherapp.features.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.weatherapp.features.app.App
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val mainRepository = (application as App).restApi.mainRepository
    internal val weatherLiveData = MutableLiveData<WeatherModel>()
    val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e ->
            showMessage(e)

        }

    private fun showMessage(e: Throwable) {
        Log.d("msg", "show message")
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun getWeather(city: String) = viewModelScope.launch(coroutineContext) {
        mainRepository.getWether(city)?.let {
            val city: String? = it.let { it.city?.name }
            val temperature: List<Weather?>? =
                it.weatherList?.map { Weather(it?.dtTxt, it.main?.temp) }
            val weatherModel = WeatherModel(city, temperature)
            weatherLiveData.value = weatherModel
        }
    }
}