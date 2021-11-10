package com.rk.weatherapp.data.repositories.http

import com.rk.weatherapp.data.repositories.http.modal.CurrentWeatherDataApiResponse
import com.rk.weatherapp.data.repositories.http.modal.toDomainWeather
import com.rk.weatherapp.domain.entities.*
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

    override suspend fun getWeatherByCityId(cityId: Long): Result<Weather, Exception> {

        val resp = kotlin.runCatching {
            apiManager.currentWeatherData(
                id = cityId,
                appId = appId,
                lang = "en",
                units = "Metric"
            ).execute()
        }.getOrElse {
            return Failure(IOException())
        }

        if (resp.body() == null) {
            return Failure(Exception())
        }

        return Success(resp.body()!!.toDomainWeather())
    }

    override suspend fun getWeatherByGeographic(coordinator: Coordinator): Result<Weather, Exception> {
        val resp = kotlin.runCatching {
            apiManager.currentWeatherData(
                lat = coordinator.latitude,
                lon = coordinator.longitude,
                appId = appId,
                lang = "en",
                units = "Metric"
            ).execute()
        }.getOrElse {
            return Failure(IOException())
        }

        if (resp.body() == null) {
            return Failure(Exception())
        }

        return Success(resp.body()!!.toDomainWeather())
    }

}