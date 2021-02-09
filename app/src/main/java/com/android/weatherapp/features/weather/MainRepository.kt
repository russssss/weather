package com.android.weatherapp.features.weather

import com.android.weatherapp.features.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getWether() = apiHelper.getWether()
}