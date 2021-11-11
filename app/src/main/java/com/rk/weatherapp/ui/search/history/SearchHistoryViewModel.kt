package com.rk.weatherapp.ui.search.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather

class SearchHistoryViewModel : ViewModel() {

    val cities = MutableLiveData<List<City>>()


    fun updateWeather(weather: Weather) {
        val copyOfCities = cities.value


        if (copyOfCities != null) {
            copyOfCities.forEach {
                if (it.id == weather.cityId) {
                    it.weather = weather
                }
            }

            cities.postValue(copyOfCities!!)
        }

    }

}