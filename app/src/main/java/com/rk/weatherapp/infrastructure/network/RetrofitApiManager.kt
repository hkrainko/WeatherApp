package com.rk.weatherapp.infrastructure.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiManager {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        private val manager = RetrofitApiManager()
        val client: Retrofit
            get() = manager.retrofit
    }

}