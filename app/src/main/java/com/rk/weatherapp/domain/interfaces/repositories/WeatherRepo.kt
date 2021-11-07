package com.rk.weatherapp.domain.interfaces.repositories

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather

interface WeatherRepo {

    fun getWeatherForCity(cityId: String, callback: () -> Weather)
}