package com.rk.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.data.repositories.http.OpenWeatherWeatherRepo
import com.rk.weatherapp.data.repositories.realm.RealmCityRepo
import com.rk.weatherapp.domain.entities.*
import com.rk.weatherapp.domain.interfaces.usecases.SearchHistoryUseCase
import com.rk.weatherapp.domain.interfaces.usecases.WeatherUseCase
import com.rk.weatherapp.domain.usecases.DefaultSearchHistoryUseCase
import com.rk.weatherapp.domain.usecases.DefaultWeatherUseCase
import com.rk.weatherapp.infrastructure.database.RealmDBManager
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val weatherUseCase: WeatherUseCase = DefaultWeatherUseCase(OpenWeatherWeatherRepo())
    private val searchHistoryUseCase: SearchHistoryUseCase =
        DefaultSearchHistoryUseCase(RealmCityRepo(RealmDBManager.realm))

    val cities = MutableLiveData<List<City>>()
    val localCityWeather = MutableLiveData<Weather?>()
    val historyCities = MutableLiveData<List<City>>()

    // UI events
    fun onResume() {
        getHistory()
    }

    fun onQueryTextChange(q: String) {
        if (q.isEmpty()) {
            cities.value = emptyList()
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
                    cities.value = result.value!!
                }
                is Failure -> {
                    Log.d("MainViewModel", "Failure:${result.reason}")
                    cities.value = emptyList()
                }
            }
        }
    }

    fun onClickSearchResult(cityId: Long) {
        // TODO: async call
        runBlocking {
            searchHistoryUseCase.setLastAccessedCity(cityId)
        }
    }

    fun onClickSearchHistory(cityId: Long) {
        runBlocking {
            searchHistoryUseCase.setLastAccessedCity(cityId)
        }
    }

    fun onLocationUpdate(lat: Double, lon: Double) {
        getWeatherByCoordinator(Coordinator(lat, lon))
    }

    // private methods
    private fun getWeatherByCoordinator(coordinator: Coordinator) {
        CoroutineScope(Dispatchers.IO).async {
            when (val weather = weatherUseCase.getWeatherByGeographic(coordinator)) {
                is Success -> {
                    localCityWeather.postValue(weather.value)
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
            when (val result = searchHistoryUseCase.getLastAccessedCities(5)) {
                is Success -> {
                    historyCities.value = result.value!!
                }
                is Failure -> {
                    historyCities.value = emptyList()
                }
            }
        }
    }

}