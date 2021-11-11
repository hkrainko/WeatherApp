package com.rk.weatherapp.domain.usecases

import com.rk.weatherapp.data.repositories.realm.RealmCityRepo
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.interfaces.repositories.CityRepo
import com.rk.weatherapp.domain.interfaces.usecases.SearchHistoryUseCase
import java.lang.Exception

class DefaultSearchHistoryUseCase(
    private val cityRepo: CityRepo
): SearchHistoryUseCase {

    override suspend fun searchCitiesByName(name: String): Result<List<City>, Exception> {
        return cityRepo.getCitiesByName(name, 20)
    }

    override suspend fun getSearchHistory(size: Int): Result<List<City>, Exception> {
        return cityRepo.getLastAccessedCities(size)
    }

    override suspend fun setSearchHistory(cityId: Long): Result<Unit, Exception> {
        return cityRepo.updateCityLastAccessedTime(cityId, System.currentTimeMillis())
    }

    override suspend fun removeCityFromSearchHistory(cityId: Long): Result<Unit, Exception> {
        return cityRepo.updateCityLastAccessedTime(cityId, null)
    }


}