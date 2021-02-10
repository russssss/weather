package com.android.testmvvm.features.db

import android.content.Context
import androidx.room.Room

class StorageApi(context: Context) {

    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-weather"
        ).build()
    }

    fun setData(vararg weatherStorageData: WeatherStorageData) {
        val userDao = db.weatherDao()
        userDao.insertAll(*weatherStorageData)
    }

    fun getAll(): List<WeatherStorageData> {
        val weatherDao = db.weatherDao()
        return weatherDao.getAll()
    }

    fun getByCity(city: String): WeatherStorageData {
        val weatherDao = db.weatherDao()
        return weatherDao.findByName(city)
    }
}