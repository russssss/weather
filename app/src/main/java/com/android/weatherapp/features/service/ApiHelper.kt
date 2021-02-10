package com.android.weatherapp.features.service

class ApiHelper(private val apiService: ApiService) {

    suspend fun getWether(city: String) =
        apiService.getWeather(
            "2.5",
            city,
            "ru",
            "metric",
            7,
            "47652cf84ade3d26d19fefb1fdfc42c3"
        )
}