package com.android.weatherapp.db

import com.google.gson.annotations.SerializedName

data class WeatherWeekStorageData(
    @SerializedName("date") val date: String?,
    @SerializedName("temp") val temp: Double?
)
