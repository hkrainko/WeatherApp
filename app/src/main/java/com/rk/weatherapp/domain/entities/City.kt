package com.rk.weatherapp.domain.entities

data class City(
    val id: Long,
    var weather: Weather?,
    var name: String?,
    var country: String?,
)