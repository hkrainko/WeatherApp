package com.rk.weatherapp.domain.entities


data class Weather(

    val condition: WeatherCondition?,

    val temp: Double?,
    val feelsLike: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val pressure: Double?,
    val humidity: Double?,

    val sunset: Long?,
    val sunrise: Long?,
)

data class WeatherCondition(
    val desc: String,
    val type: Type
) {
    enum class Type {
        Thunderstorm,
        Drizzle,
        Rain,
        Snow,
        Atmosphere,
        Clear,
        Clouds,
        Unknown
    }
}