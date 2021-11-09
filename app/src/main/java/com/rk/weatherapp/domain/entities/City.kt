package com.rk.weatherapp.domain.entities

data class City(
    val id: Long,
    val weather: Weather?,
    val name: String?,
    val country: String?,
)