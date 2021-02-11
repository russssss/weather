package com.android.weatherapp.service

import com.android.weatherapp.dto.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/data/{api}/forecast/daily")
    suspend fun getWeather(
        @Path("api") api: String,
        @Query("q") q: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String
    ): WeatherData //Deferred<Response<WeatherData>>
}