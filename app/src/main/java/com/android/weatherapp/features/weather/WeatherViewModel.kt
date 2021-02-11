package com.android.weatherapp.features.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.testmvvm.features.db.StorageApi
import com.android.testmvvm.features.db.WeatherStorageData
import com.android.weatherapp.features.app.App
import com.android.weatherapp.features.db.WeatherWeekStorageData
import com.android.weatherapp.utils.TimeUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val mainRepository = (application as App).restApi.mainRepository
    val db = (application as App).db
    internal val weatherLiveData = MutableLiveData<WeatherModel>()
    val job = SupervisorJob()
    lateinit var storageApi: StorageApi;

    init {
        storageApi = StorageApi(application)
    }

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

    fun getWeather(cityName: String) = viewModelScope.launch(coroutineContext) {

        var weatherModel: WeatherModel? = null

        withContext(Dispatchers.IO) {
            db.weatherDao().findByName(cityName)?.let {
                val temperature: List<Weather?>? =
                    it.weekweather?.map { Weather(it?.date, it?.temp) }
                weatherModel = WeatherModel(it.city, temperature)
            }
        }

        weatherModel?.let { weatherLiveData.value = weatherModel }

        mainRepository.getWether(cityName)?.let {
            val city: String? = it.let { it.city?.name }
            val temperature: List<Weather?>? =
                it.weatherList?.map {
                    Weather(
                        TimeUtils.formatTime(it?.dt ?: 0),
                        it.temp?.day
                    )
                }
            var weatherModel = WeatherModel(city, temperature)

            if (weatherModel != null) {
                withContext(Dispatchers.IO) {
                    val temperatureDB: List<WeatherWeekStorageData?>? =
                        it.weatherList?.map {
                            WeatherWeekStorageData(
                                it?.dtTxt,
                                it.temp?.day
                            )
                        }
                    val d = WeatherStorageData(0, cityName, temperatureDB);
                    db.weatherDao().insertAll(d)
                }
                weatherLiveData.value = weatherModel
            }
        }
    }
}