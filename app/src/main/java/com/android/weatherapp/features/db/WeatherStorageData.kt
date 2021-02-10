package com.android.testmvvm.features.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.android.weatherapp.features.db.WeatherWeekStorageData

@Entity(tableName = "running_table", indices = [Index(value = arrayOf("city"), unique = true)])
data class WeatherStorageData(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "weekweather") val weekweather: List<WeatherWeekStorageData?>?
)