package com.rk.weatherapp.domain.interfaces.usecases

import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Weather
import java.lang.Exception

interface WeatherUseCase {

    suspend fun getWeatherForCity(cityId: String): Result<Weather, Exception>
}