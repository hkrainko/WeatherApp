package com.rk.weatherapp.domain.usecases

import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo
import com.rk.weatherapp.domain.interfaces.usecases.WeatherUseCase
import java.lang.Exception

class DefaultWeatherUseCase(
    private val weatherRepo: WeatherRepo
): WeatherUseCase {

    override suspend fun getWeatherForCity(cityId: String): Result<Weather, Exception> {
        return weatherRepo.getWeatherForCity(cityId)
    }
}