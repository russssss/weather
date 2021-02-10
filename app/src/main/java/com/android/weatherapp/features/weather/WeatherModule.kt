package com.android.weatherapp.features.weather

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherModule() {
    @Singleton
    @Provides
    fun provideWeatherViewModel(app: Application): WeatherViewModel {
        return WeatherViewModel(app)
    }
}