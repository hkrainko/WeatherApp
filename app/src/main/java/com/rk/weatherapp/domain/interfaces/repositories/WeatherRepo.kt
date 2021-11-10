package com.rk.weatherapp.domain.interfaces.repositories

import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.entities.Result
import java.lang.Exception

interface WeatherRepo {

    suspend fun getWeatherByCityId(cityId: String): Result<Weather, Exception>
}