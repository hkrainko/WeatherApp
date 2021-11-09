package com.rk.weatherapp.data.repositories.http

import com.rk.weatherapp.data.repositories.http.modal.CurrentWeatherDataApiResponse
import com.rk.weatherapp.data.repositories.http.modal.toDomainWeather
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo
import com.rk.weatherapp.infrastructure.network.RetrofitApiManager
import com.rk.weatherapp.infrastructure.network.RetrofitApiService
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class OpenWeatherWeatherRepo() : WeatherRepo {

    val appId = "7b086d9efe6240421f6963733b0773af"

    private val apiManager: RetrofitApiService =
        RetrofitApiManager.client.create(RetrofitApiService::class.java)

    override suspend fun getWeatherForCity(cityId: String): Result<Weather, Exception> {

        val resp = kotlin.runCatching {
            apiManager.currentWeatherData(cityId, appId, "zh_tw", "Metric").execute()
        }.getOrElse {
            return Failure(IOException())
        }

        if (resp.body() == null) {
            return Failure(Exception())
        }

        return Success(resp.body()!!.toDomainWeather())
    }

}