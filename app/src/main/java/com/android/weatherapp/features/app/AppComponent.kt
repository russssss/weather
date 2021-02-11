package com.android.weatherapp.features.app

import com.android.weatherapp.features.city.CityActivity
import com.android.weatherapp.features.city.CityModule
import com.android.weatherapp.features.weather.WeatherActivity
import com.android.weatherapp.features.weather.WeatherModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CityModule::class, WeatherModule::class])
interface AppComponent {
    fun inject(cityActivity: CityActivity)
    fun inject(weatherActivity: WeatherActivity)
}