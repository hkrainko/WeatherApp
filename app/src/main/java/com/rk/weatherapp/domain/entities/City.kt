package com.rk.weatherapp.domain.entities

data class City(
    val id: String,
    val weather: Weather?,
    val nameEn: String?,
    val nameZh: String?,
    val country: String?,
    val coordinate: Coordinate?,
)


data class Coordinate(
    val lon: Double,
    val lat: Double,
)
