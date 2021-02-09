package com.android.weatherapp.features.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun getWether() = apiService.getWeather()
}