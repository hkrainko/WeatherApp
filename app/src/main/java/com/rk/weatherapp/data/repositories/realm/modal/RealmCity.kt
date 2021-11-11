package com.rk.weatherapp.data.repositories.realm

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.entities.WeatherCondition
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmCity(
    @PrimaryKey
    var id: Long = -1,

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

    var conditionDesc: String? = null,
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
    Mist("Mist"),
    Smoke("Smoke"),
    Haze("Haze"),
    Dust("Dust"),
    Fog("Fog"),
    Sand("Sand"),
    Ash("Ash"),
    Squall("Squall"),
    Tornado("Tornado"),
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
        RealmWeatherConditionType.Mist -> WeatherCondition.Type.Mist
        RealmWeatherConditionType.Smoke -> WeatherCondition.Type.Smoke
        RealmWeatherConditionType.Haze -> WeatherCondition.Type.Haze
        RealmWeatherConditionType.Dust -> WeatherCondition.Type.Dust
        RealmWeatherConditionType.Fog -> WeatherCondition.Type.Fog
        RealmWeatherConditionType.Sand -> WeatherCondition.Type.Sand
        RealmWeatherConditionType.Ash -> WeatherCondition.Type.Ash
        RealmWeatherConditionType.Squall -> WeatherCondition.Type.Squall
        RealmWeatherConditionType.Tornado -> WeatherCondition.Type.Tornado
        RealmWeatherConditionType.Clear -> WeatherCondition.Type.Clear
        RealmWeatherConditionType.Clouds -> WeatherCondition.Type.Clouds
        else -> WeatherCondition.Type.Unknown
    }
}

fun WeatherCondition.Type.toRealmWeatherConditionType(): RealmWeatherConditionType {
    return when (this) {
        WeatherCondition.Type.Thunderstorm -> RealmWeatherConditionType.Thunderstorm
        WeatherCondition.Type.Drizzle -> RealmWeatherConditionType.Drizzle
        WeatherCondition.Type.Rain -> RealmWeatherConditionType.Rain
        WeatherCondition.Type.Snow -> RealmWeatherConditionType.Snow
        WeatherCondition.Type.Atmosphere -> RealmWeatherConditionType.Atmosphere
        WeatherCondition.Type.Clear -> RealmWeatherConditionType.Clear
        WeatherCondition.Type.Clouds -> RealmWeatherConditionType.Clouds
        else -> RealmWeatherConditionType.Unknown
    }
}

fun RealmCity.toDomainCity(): City {

    val weatherCondition = WeatherCondition(
        conditionDesc,
        conditionTypeEnum.toDomainWeatherCondition()
    )

    val weather = Weather(
        id,
        name,
        weatherCondition,
        temp,
        feelsLike,
        tempMin,
        tempMax,
        pressure,
        humidity,
        sunset,
        sunrise,
        lastUpdatedTime,
    )

    return City(
        id,
        weather,
        name,
        country,
    )
}