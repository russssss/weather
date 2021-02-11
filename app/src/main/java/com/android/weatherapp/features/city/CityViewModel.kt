package com.android.weatherapp.features.city

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.testmvvm.features.db.WeatherStorageData
import com.android.weatherapp.features.app.App
import com.android.weatherapp.features.db.WeatherWeekStorageData
import com.android.weatherapp.features.weather.Weather
import com.android.weatherapp.features.weather.WeatherModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CityViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val mainRepository = (application as App).restApi.mainRepository
    val db = (application as App).db
    internal val weatherLiveData = MutableLiveData<List<WeatherModel?>>()
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

    fun getWeather(vararg cityName: String) = viewModelScope.launch(coroutineContext) {

        var isUpdated = false
        var weatherModelList: List<WeatherModel?>? = null

        withContext(Dispatchers.IO) {
            weatherModelList = db.weatherDao().getAll().map {
                val temperature: List<Weather?>? =
                    it.weekweather?.map { Weather(it?.date, it?.temp) }
                WeatherModel(it.city, temperature)
            }
        }

        weatherModelList?.let { weatherLiveData.value = weatherModelList }

        cityName.map {
            val city = it
            mainRepository.getWether(city)?.let {
                val city: String? = it.let { it.city?.name }
                val temperature: List<Weather?>? =
                    it.weatherList?.map { Weather(it?.dtTxt, it.main?.temp) }
                var weatherModel = WeatherModel(city, temperature)

                if (weatherModel != null) {
                    withContext(Dispatchers.IO) {
                        val temperatureDB: List<WeatherWeekStorageData?>? =
                            it.weatherList?.map { WeatherWeekStorageData(it?.dtTxt, it.main?.temp) }
                        val d = WeatherStorageData(0, city, temperatureDB);
                        db.weatherDao().insertAll(d)
                    }
                }

                weatherModel
            }
        }.let {
            withContext(Dispatchers.IO) {
                weatherModelList = db.weatherDao().getAll().map {
                    val temperature: List<Weather?>? =
                        it.weekweather?.map { Weather(it?.date, it?.temp) }
                    WeatherModel(it.city, temperature)
                }
            }
            weatherModelList?.let { weatherLiveData.value = weatherModelList }
        }
    }
}