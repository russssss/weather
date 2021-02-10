package com.android.weatherapp.features.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys {
    @SerializedName("pod")
    @Expose
    var pod: String? = null
}