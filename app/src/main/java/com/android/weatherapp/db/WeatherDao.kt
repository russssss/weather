package com.android.testmvvm.features.db

import androidx.room.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM running_table ORDER BY city")
    fun getAll(): List<WeatherStorageData>

    @Query("SELECT * FROM running_table WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<WeatherStorageData>

    @Query("SELECT * FROM running_table WHERE city LIKE :city LIMIT 1")
    fun findByName(city: String): WeatherStorageData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg weatherStorageData:  WeatherStorageData)

    @Delete
    fun delete(weatherStorageData: WeatherStorageData)
}