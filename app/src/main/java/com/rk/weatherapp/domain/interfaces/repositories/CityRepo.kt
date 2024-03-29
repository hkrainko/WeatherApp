package com.rk.weatherapp.domain.interfaces.repositories

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Weather
import java.lang.Exception

interface CityRepo {
    suspend fun getCitiesByName(name: String, maxSize: Int): Result<List<City>, Exception>
    suspend fun getLastAccessedCities(size: Int): Result<List<City>, Exception>
    suspend fun updateCityLastAccessedTime(cityId: Long, lastAccessTime: Long?): Result<Unit, Exception>
    suspend fun updateCity(weather: Weather): Result<Unit, Exception>
}