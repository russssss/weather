package com.android.weatherapp.features.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.weatherapp.features.service.ApiHelper
import com.android.weatherapp.features.service.ApiService
import com.android.weatherapp.features.service.MainRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val mock = mutableListOf<String>("+7", "+8")
    lateinit var mainRepository: MainRepository
    internal val weatherLiveData = MutableLiveData<WeatherModel>()
    val job = SupervisorJob()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//            .registerTypeAdapter(WeatherData::class.java, WeatherJ)
            .create()

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://api.openweathermap.org/data/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


        val apiService = retrofit.create(ApiService::class.java)
        mainRepository = MainRepository(apiHelper = ApiHelper(apiService))
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun getWeather() = viewModelScope.launch(coroutineContext) {
        mainRepository.getWether("Moscow")?.let {
            val city: String? = it.let { it.city?.name }
            val temperature: List<Weather?>? =
                it.weatherList?.map { Weather(it?.dtTxt, it.main?.temp) }
            var weatherModel = WeatherModel(city, temperature)
            weatherLiveData.value = weatherModel
        }
    }
}