package com.rk.weatherapp.infrastructure

import com.rk.weatherapp.domain.entities.WeatherCondition

fun WeatherCondition.Type.toOpenWeatherUrl(): String? {
    val path = "https://openweathermap.org/img/wn/"
    return when (this) {
        WeatherCondition.Type.Thunderstorm -> "${path}11d@2x.png"
        WeatherCondition.Type.Drizzle -> "${path}09d@2x.png"
        WeatherCondition.Type.Rain -> "${path}10d@2x.png"
        WeatherCondition.Type.Snow -> "${path}13d@2x.png"
        WeatherCondition.Type.Atmosphere -> "${path}50d@2x.png"
        WeatherCondition.Type.Mist -> "${path}50d@2x.png"
        WeatherCondition.Type.Smoke -> "${path}50d@2x.png"
        WeatherCondition.Type.Haze -> "${path}50d@2x.png"
        WeatherCondition.Type.Dust -> "${path}50d@2x.png"
        WeatherCondition.Type.Fog -> "${path}50d@2x.png"
        WeatherCondition.Type.Sand -> "${path}50d@2x.png"
        WeatherCondition.Type.Ash -> "${path}50d@2x.png"
        WeatherCondition.Type.Squall -> "${path}50d@2x.png"
        WeatherCondition.Type.Tornado -> "${path}50d@2x.png"
        WeatherCondition.Type.Clear -> "${path}01d@2x.png"
        WeatherCondition.Type.Clouds -> "${path}02d@2x.png"
        WeatherCondition.Type.Unknown -> null
        else -> null
    }
}