package com.android.weatherapp.features.app

import android.app.Application
import androidx.room.Room
import com.android.testmvvm.features.db.AppDatabase
import com.android.weatherapp.service.RestApi

class App : Application(), IApp {

    internal lateinit var db: AppDatabase
    internal lateinit var appComponent: AppComponent
    internal lateinit var restApi: RestApi

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        restApi = RestApi()

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-weather"
        ).build()
    }
}