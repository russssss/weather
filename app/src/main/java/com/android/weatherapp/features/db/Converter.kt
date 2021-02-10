package com.android.weatherapp.features.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {
    @TypeConverter
    fun fromWeatherDao(weatherWeekStorageData: List<WeatherWeekStorageData?>?): String? {
        return Gson().toJson(weatherWeekStorageData)
    }

    //    @TypeConverter
//    fun toWeatherDao(json: String): List<WeatherWeekStorageData?>? {
//        return Gson().fromJson(json, WeatherWeekStorageData::class.java)
//    }
    @TypeConverter
    fun toWeatherDao(json: String): List<WeatherWeekStorageData?>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherWeekStorageData?>?>() {}.type
        val productCategoriesList: List<WeatherWeekStorageData> = gson.fromJson(
            json,
            type
        )
        return productCategoriesList
    }
}