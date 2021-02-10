package com.android.weatherapp.features.service

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    val mainRepository: MainRepository

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://api.openweathermap.org/data/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


        val apiService = retrofit.create(ApiService::class.java)
        mainRepository = MainRepository(apiHelper = ApiHelper(apiService))
    }
}