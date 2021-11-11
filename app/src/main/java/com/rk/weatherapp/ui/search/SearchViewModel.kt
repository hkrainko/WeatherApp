package com.rk.weatherapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.domain.entities.City

class SearchViewModel : ViewModel() {

    val cities: MutableLiveData<List<City>> = MutableLiveData()

    fun updateCities(cities: List<City>) {
        this.cities.postValue(cities)
    }
}