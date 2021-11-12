package com.rk.weatherapp.domain.usecases

import android.util.Log
import com.rk.weatherapp.domain.entities.*
import com.rk.weatherapp.domain.interfaces.repositories.CityRepo
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo
import com.rk.weatherapp.domain.interfaces.usecases.WeatherUseCase
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.lang.Exception

class DefaultWeatherUseCase(
    private val weatherRepo: WeatherRepo,
    private val cityRepo: CityRepo
) : WeatherUseCase {

    override suspend fun getWeatherByCityId(cityId: Long): Result<Weather, Exception> {

        val weather = weatherRepo.getWeatherByCityId(cityId)
        if (weather is Success) {
            CoroutineScope(Dispatchers.Main).async {
                cityRepo.updateCity(weather.value)
            }
        }
        return weather
    }

    override suspend fun getWeatherByGeographic(coordinator: Coordinator): Result<Weather, Exception> {
        val weather = weatherRepo.getWeatherByGeographic(coordinator)
        if (weather is Success) {
            CoroutineScope(Dispatchers.Main).async {
                cityRepo.updateCity(weather.value)
            }
        }
        return weather
    }
}