package com.android.weatherapp.features.city

import android.app.Application
import com.android.testmvvm.features.db.AppDatabase
import com.android.weatherapp.service.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityModule() {

    @Singleton
    @Provides
    fun provideCityViewModel(
        db: AppDatabase,
        mainRepository: MainRepository,
        application: Application
    ): CityViewModel {
        return CityViewModel(db, mainRepository, application)
    }
}