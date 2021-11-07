package com.rk.weatherapp.domain.interfaces.repositories

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Result
import java.lang.Exception

interface CityRepo {
    suspend fun getCitiesByName(name: String): Result<List<City>, Exception>
}