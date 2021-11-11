package com.rk.weatherapp.infrastructure

import com.rk.weatherapp.domain.entities.WeatherCondition
import org.junit.Assert.*
import org.junit.Test

class WeatherConditionExtTest {
    @Test
    fun toOpenWeatherUrl_isCorrect() {
        val weatherConditionType = WeatherCondition.Type.Thunderstorm

        assertEquals("https://openweathermap.org/img/wn/11d@2x.png", weatherConditionType.toOpenWeatherUrl())
    }
}