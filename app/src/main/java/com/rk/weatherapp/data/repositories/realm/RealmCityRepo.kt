package com.rk.weatherapp.data.repositories.realm

import android.util.Log
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.interfaces.repositories.CityRepo
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.*
import java.lang.Exception

class RealmCityRepo(
    private val realm: Realm
): CityRepo {

    override suspend fun getCitiesByName(name: String): Result<List<City>, Exception> {
        Log.v("RealmCityRepo", "Fetched object by name: $name")

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

}