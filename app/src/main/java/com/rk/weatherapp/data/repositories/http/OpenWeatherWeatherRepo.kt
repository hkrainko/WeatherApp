package com.rk.weatherapp.data.repositories.http

import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo

class OpenWeatherWeatherRepo: WeatherRepo {
    override fun getWeatherForCity(cityId: String, callback: () -> Weather) {
        TODO("Not yet implemented")
    }
}