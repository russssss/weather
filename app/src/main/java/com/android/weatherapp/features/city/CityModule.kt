package com.android.weatherapp.features.city

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityModule() {
    @Singleton
    @Provides
    fun provideCityViewModel(app: Application): CityViewModel {
        return CityViewModel(app)
    }
}