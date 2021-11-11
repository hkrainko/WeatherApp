package com.rk.weatherapp.domain.entities


data class Weather(

    val cityId: Long,
    val cityName: String?,

    val condition: WeatherCondition?,

    val temp: Double?,
    val feelsLike: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val pressure: Long?,
    val humidity: Long?,

    val sunset: Long?,
    val sunrise: Long?,

    val lastUpdatedTime: Long?
)

data class WeatherCondition(
    val desc: String?,
    val type: Type
) {
    enum class Type {
        Thunderstorm,
        Drizzle,
        Rain,
        Snow,
        Atmosphere,
        Mist,
        Smoke,
        Haze,
        Dust,
        Fog,
        Sand,
        Ash,
        Squall,
        Tornado,
        Clear,
        Clouds,
        Unknown
    }
}