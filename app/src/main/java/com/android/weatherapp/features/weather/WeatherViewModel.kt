package com.android.weatherapp.features.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.testmvvm.features.db.AppDatabase
import com.android.testmvvm.features.db.WeatherStorageData
import com.android.weatherapp.db.WeatherWeekStorageData
import com.android.weatherapp.service.MainRepository
import com.android.weatherapp.utils.TimeUtils
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(val db: AppDatabase, val mainRepository: MainRepository, application: Application) : AndroidViewModel(application), CoroutineScope {

    internal val weatherLiveData = MutableLiveData<WeatherModel>()
    private val job = SupervisorJob()
    internal var onMessage: ((String) -> Unit)? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e ->
            val error = when (e) {
                is UnknownHostException -> "Connection error"
                is HttpException -> "No city found"
                is SocketTimeoutException, is SSLHandshakeException, is ConnectException -> "Server timeout error"
                else -> "Unexpected error"
            }
            onMessage?.invoke(error)
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
                                TimeUtils.formatTime(it?.dt ?: 0),
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