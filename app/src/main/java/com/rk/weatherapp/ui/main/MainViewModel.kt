package com.rk.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.data.repositories.http.OpenWeatherWeatherRepo
import com.rk.weatherapp.data.repositories.realm.RealmCityRepo
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.entities.Weather
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

    // UI events
    fun onResume() {
        CoroutineScope(Dispatchers.IO).async {
            when (val weather = weatherUseCase.getWeatherByCityId("1821993")) {
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

        // TODO: get history city weather
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

}