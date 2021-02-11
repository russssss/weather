package com.android.weatherapp.features.app

import android.app.Application
import com.android.testmvvm.features.db.AppDatabase
import com.android.weatherapp.service.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun provideRepository(application: Application): MainRepository {
        return (application as App).restApi.mainRepository
    }

    @Singleton
    @Provides
    fun provideDb(application: Application): AppDatabase {
        return (application as App).db
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }
}