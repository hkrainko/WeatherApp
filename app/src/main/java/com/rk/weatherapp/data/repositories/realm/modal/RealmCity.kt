package com.rk.weatherapp.data.repositories.realm

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.entities.WeatherCondition
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmCity(
    @PrimaryKey
    var id: String = "",

    @Required
    var name: String? = null,
    var country: String? = null,

    // weather
    var temp: Double? = null,
    var feelsLike: Double? = null,
    var tempMin: Double? = null,
    var tempMax: Double? = null,
    var pressure: Long? = null,
    var humidity: Long? = null,

    var sunset: Long? = null,
    var sunrise: Long? = null,

    var conditionDesc: String = "",
    var conditionType: String = RealmWeatherConditionType.Unknown.desc,

    // access info
    var lastAccessTime: Long? = null,
    var lastUpdatedTime: Long? = null,

    ) : RealmObject() {

    var conditionTypeEnum: RealmWeatherConditionType
        get() {
            return try {
                RealmWeatherConditionType.valueOf(conditionType)
            } catch (e: IllegalArgumentException) {
                RealmWeatherConditionType.Unknown
            }
        }
        set(value) {}

}

enum class RealmWeatherConditionType(val desc: String) {
    Thunderstorm("Thunderstorm"),
    Drizzle("Drizzle"),
    Rain("Rain"),
    Snow("Snow"),
    Atmosphere("Atmosphere"),
    Clear("Clear"),
    Clouds("Clouds"),
    Unknown("Unknown")
}

fun RealmWeatherConditionType.toDomainWeatherCondition(): WeatherCondition.Type {
    return when (this) {
        RealmWeatherConditionType.Thunderstorm -> WeatherCondition.Type.Thunderstorm
        RealmWeatherConditionType.Drizzle -> WeatherCondition.Type.Drizzle
        RealmWeatherConditionType.Rain -> WeatherCondition.Type.Rain
        RealmWeatherConditionType.Snow -> WeatherCondition.Type.Snow
        RealmWeatherConditionType.Atmosphere -> WeatherCondition.Type.Atmosphere
        RealmWeatherConditionType.Clear -> WeatherCondition.Type.Clear
        RealmWeatherConditionType.Clouds -> WeatherCondition.Type.Clouds
        else -> WeatherCondition.Type.Unknown
    }
}

fun RealmCity.toDomainCity(): City {

    val weatherCondition = WeatherCondition(
        conditionDesc,
        conditionTypeEnum.toDomainWeatherCondition()
    )

    val weather = Weather(
        weatherCondition,
        temp,
        feelsLike,
        tempMin,
        tempMax,
        pressure,
        humidity,
        sunset,
        sunrise
    )

    return City(
        id,
        weather,
        name,
        country,
    )
}