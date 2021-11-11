package com.rk.weatherapp.data.repositories.http.modal

import com.google.gson.annotations.SerializedName
import com.rk.weatherapp.data.repositories.realm.RealmWeatherConditionType
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.entities.WeatherCondition

data class CurrentWeatherDataApiResponse(

    val coord: Coord,
    val weather: List<WeatherElement>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) {
    data class Clouds(
        val all: Long
    )

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Main(
        val temp: Double,

        @SerializedName("feels_like")
        val feelsLike: Double,

        @SerializedName("temp_min")
        val tempMin: Double,

        @SerializedName("temp_max")
        val tempMax: Double,

        val pressure: Long,
        val humidity: Long,

        @SerializedName("sea_level")
        val seaLevel: Long,

        @SerializedName("grnd_level")
        val grndLevel: Long
    )

    data class Sys(
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )

    data class WeatherElement(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Wind(
        val speed: Double,
        val deg: Long,
        val gust: Double
    )
}

fun CurrentWeatherDataApiResponse.toDomainWeather(): Weather {
    return Weather(
        id,
        name,
        if (weather.isNotEmpty()) weather[0].toDomainWeatherCondition() else null,
        main.temp,
        main.feelsLike,
        main.tempMin,
        main.tempMax,
        main.pressure,
        main.humidity,
        sys.sunset,
        sys.sunrise,
        dt
    )
}

fun CurrentWeatherDataApiResponse.WeatherElement.toDomainWeatherCondition(): WeatherCondition {

    val type = when (main) {
        "Thunderstorm" -> WeatherCondition.Type.Thunderstorm
        "Drizzle" -> WeatherCondition.Type.Drizzle
        "Rain" -> WeatherCondition.Type.Rain
        "Snow" -> WeatherCondition.Type.Snow
        "Atmosphere" -> WeatherCondition.Type.Atmosphere
        "Mist" -> WeatherCondition.Type.Mist
        "Smoke" -> WeatherCondition.Type.Smoke
        "Haze" -> WeatherCondition.Type.Haze
        "Dust" -> WeatherCondition.Type.Dust
        "Fog" -> WeatherCondition.Type.Fog
        "Sand" -> WeatherCondition.Type.Sand
        "Ash" -> WeatherCondition.Type.Ash
        "Squall" -> WeatherCondition.Type.Squall
        "Tornado" -> WeatherCondition.Type.Tornado
        "Clear" -> WeatherCondition.Type.Clear
        "Clouds" -> WeatherCondition.Type.Clouds
        else -> WeatherCondition.Type.Unknown
    }
    return WeatherCondition(description, type)
}