package com.rk.weatherapp.data.repositories.realm

import android.util.Log
import com.rk.weatherapp.domain.entities.*
import com.rk.weatherapp.domain.interfaces.repositories.CityRepo
import io.realm.Case
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.lang.Exception

class RealmCityRepo(
    private val realm: Realm
) : CityRepo {

    override suspend fun getCitiesByName(name: String): Result<List<City>, Exception> {
        val rmCities = realm.where(RealmCity::class.java)
            .contains("name", name, Case.INSENSITIVE).findAll()

        val cities = rmCities.mapNotNull {
            it.toDomainCity()
        }

        return Success(cities)

//        realm.executeTransactionAwait {
//            val rmCities = realm.where(RealmCity::class.java)
//                .contains("name", name, Case.INSENSITIVE).findAll()
//
//            val cities = rmCities.mapNotNull {
//                it.toDomainCity()
//            }
//
//            return Success(cities)
//        }
    }

    override suspend fun getLastAccessedCities(size: Int): Result<List<City>, Exception> {
        val rmCities = realm.where(RealmCity::class.java)
            .sort("lastAccessTime", Sort.DESCENDING)
            .limit(size.toLong())
            .findAll()

        val cities = rmCities.mapNotNull {
            it.toDomainCity()
        }
        return Success(cities)
    }

    override suspend fun updateCityLastAccessedTime(cityId: Long): Result<Unit, Exception> {
        realm.executeTransactionAwait { r: Realm ->
            val realmCity = r.where(RealmCity::class.java)
                .equalTo("id", cityId)
                .findFirst()
            if (realmCity != null) {
                realmCity.lastAccessTime = System.currentTimeMillis()
            }
        }
        return Success(Unit)
    }

    override suspend fun updateCity(weather: Weather): Result<Unit, Exception> {
        realm.executeTransactionAwait { r: Realm ->
            val realmCity = r.where(RealmCity::class.java)
                .equalTo("id", weather.cityId)
                .findFirst()
            if (realmCity != null) {
                realmCity.name = weather.cityName // need to update?
                realmCity.conditionTypeEnum =
                    weather.condition?.type?.toRealmWeatherConditionType()
                        ?: RealmWeatherConditionType.Unknown
                realmCity.conditionDesc = weather.condition?.desc
                realmCity.temp = weather.temp
                realmCity.feelsLike = weather.feelsLike
                realmCity.tempMin = weather.tempMin
                realmCity.tempMax = weather.tempMax
                realmCity.pressure = weather.pressure
                realmCity.humidity = weather.humidity
                realmCity.sunset = weather.sunset
                realmCity.sunrise = weather.sunrise
                realmCity.lastUpdatedTime = weather.lastUpdatedTime
            }
        }
        return Success(Unit)
    }


}