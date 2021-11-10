package com.rk.weatherapp.domain.interfaces.usecases

import com.rk.weatherapp.domain.entities.Coordinator
import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Weather
import java.lang.Exception

interface WeatherUseCase {

    suspend fun getWeatherByCityId(cityId: Long): Result<Weather, Exception>
    suspend fun getWeatherByGeographic(coordinator: Coordinator): Result<Weather, Exception>
}