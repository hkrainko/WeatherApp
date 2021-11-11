package com.rk.weatherapp.domain.interfaces.usecases

import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Result
import java.lang.Exception

interface SearchHistoryUseCase {

    suspend fun searchCitiesByName(name: String): Result<List<City>, Exception>
    suspend fun getSearchHistory(size: Int): Result<List<City>, Exception>
    suspend fun setSearchHistory(cityId: Long): Result<Unit, Exception>
    suspend fun removeCityFromSearchHistory(cityId: Long): Result<Unit, Exception>

}