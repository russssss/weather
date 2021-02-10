package com.android.weatherapp.features.app

import android.app.Application
import com.android.weatherapp.features.service.RestApi

class App : Application(), IApp {

    lateinit var appComponent: AppComponent
    lateinit var restApi: RestApi

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        restApi = RestApi()
    }
}