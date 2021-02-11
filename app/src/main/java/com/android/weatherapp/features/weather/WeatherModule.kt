package com.android.weatherapp.features.weather

import android.app.Application
import com.android.testmvvm.features.db.AppDatabase
import com.android.weatherapp.service.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherModule() {

    @Singleton
    @Provides
    fun provideWeatherViewModel(
        db: AppDatabase,
        mainRepository: MainRepository,
        application: Application
    ): WeatherViewModel {
        return WeatherViewModel(db, mainRepository, application)
    }
}