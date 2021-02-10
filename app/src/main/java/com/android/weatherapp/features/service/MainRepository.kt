package com.android.weatherapp.features.service

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getWether(city: String) = apiHelper.getWether(city)
}