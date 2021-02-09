package com.android.weatherapp.features.dto

import com.google.gson.annotations.SerializedName

data class WetherData(
    @SerializedName("weather")
    val weather: String
)
