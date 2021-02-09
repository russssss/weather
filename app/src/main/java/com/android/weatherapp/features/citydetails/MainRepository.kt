package com.android.weatherapp.features.citydetails

import com.android.weatherapp.features.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getWether() = apiHelper.getWether()
}