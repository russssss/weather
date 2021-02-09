package com.android.weatherapp.features.service

import com.android.weatherapp.features.dto.WetherData
import retrofit2.http.GET

interface ApiService {
    @GET("weather")
    suspend fun getWeather(): List<WetherData>
}
