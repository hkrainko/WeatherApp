package com.rk.weatherapp.infrastructure.network

import com.rk.weatherapp.data.repositories.http.modal.CurrentWeatherDataApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("/data/2.5/weather")
    fun currentWeatherData(
        @Query("id") id: String,
        @Query("appid") appId: String,
        @Query("lang") lang: String,
    ): Call<CurrentWeatherDataApiResponse>
}