package com.rk.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.data.repositories.http.OpenWeatherWeatherRepo
import com.rk.weatherapp.data.repositories.realm.RealmCityRepo
import com.rk.weatherapp.domain.entities.*
import com.rk.weatherapp.domain.interfaces.usecases.SearchHistoryUseCase
import com.rk.weatherapp.domain.interfaces.usecases.WeatherUseCase
import com.rk.weatherapp.domain.usecases.DefaultSearchHistoryUseCase
import com.rk.weatherapp.domain.usecases.DefaultWeatherUseCase
import com.rk.weatherapp.infrastructure.database.RealmDBManager
import com.rk.weatherapp.ui.local.LocalCityViewModel
import com.rk.weatherapp.ui.search.SearchViewModel
import com.rk.weatherapp.ui.search.history.SearchHistoryViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val weatherUseCase: WeatherUseCase = DefaultWeatherUseCase(OpenWeatherWeatherRepo())
    private val searchHistoryUseCase: SearchHistoryUseCase =
        DefaultSearchHistoryUseCase(RealmCityRepo(RealmDBManager.realm))

    lateinit var searchHistoryVm: SearchHistoryViewModel
    lateinit var searchVm: SearchViewModel
    lateinit var localCityViewModel: LocalCityViewModel

    // UI events
    fun onResume() {
        getHistory()
    }

    fun onQueryTextChange(q: String) {
        if (q.isEmpty()) {
            searchVm.updateCities(emptyList())
            return
        }
//        GlobalScope.async {
//            when (val result = searchHistoryUseCase.searchCitiesByName(q)) {
//                is Success -> {
//                    Log.d("MainViewModel", "cities:${result.value.size}")
//                }
//                is Failure -> {
//                    Log.d("MainViewModel", "Failure:${result.reason}")
//                }
//            }
//        }
        // TODO: async call
        runBlocking {
            when (val result = searchHistoryUseCase.searchCitiesByName(q)) {
                is Success -> {
                    Log.d("MainViewModel", "cities:${result.value.size}")
                    searchVm.updateCities(result.value)
                }
                is Failure -> {
                    Log.d("MainViewModel", "Failure:${result.reason}")
                    searchVm.updateCities(emptyList())
                }
            }
        }
    }

    fun onClickSearchResult(cityId: Long) {
        // TODO: async call
        runBlocking {
            searchHistoryUseCase.setSearchHistory(cityId)
        }
    }

    fun onClickSearchHistory(cityId: Long) {
        runBlocking {
            searchHistoryUseCase.setSearchHistory(cityId)
        }
        getHistory()
    }

    fun onClickSearchHistoryDelete(cityId: Long) {
        runBlocking {
            searchHistoryUseCase.removeCityFromSearchHistory(cityId)
        }
        getHistory()
    }

    fun onLocationUpdate(lat: Double, lon: Double) {
        getWeatherByCoordinator(Coordinator(lat, lon))
    }

    // private methods
    private fun getWeatherByCoordinator(coordinator: Coordinator) {
        CoroutineScope(Dispatchers.IO).async {
            when (val result = weatherUseCase.getWeatherByGeographic(coordinator)) {
                is Success -> {
                    val city = City(result.value.cityId, result.value, result.value.cityName, null)
                    localCityViewModel.updateCity(city)
                }
                is Failure -> {
                    result.reason
                    // TODO: error handling
                }
                else -> {
                    // TODO: error handling
                }
            }
        }
    }

    private fun getWeatherByCityId(cityId: Long) {
        CoroutineScope(Dispatchers.IO).async {
            when (val weather = weatherUseCase.getWeatherByCityId(cityId)) {
                is Success -> {
                    localCityViewModel.updateWeather(weather.value)
                    searchHistoryVm.updateWeather(weather.value)
                }
                is Failure -> {
                    weather.reason
                    // TODO: error handling
                }
                else -> {
                    // TODO: error handling
                }
            }
        }
    }

    private fun getHistory() {
        runBlocking {
            when (val result = searchHistoryUseCase.getSearchHistory(5)) {
                is Success -> {
                    searchHistoryVm.cities.postValue(result.value!!)
                    result.value.forEach {
                        getWeatherByCityId(it.id)
                    }
                }
                is Failure -> {
                    searchHistoryVm.cities.postValue(emptyList())
                }
            }
        }
    }

}