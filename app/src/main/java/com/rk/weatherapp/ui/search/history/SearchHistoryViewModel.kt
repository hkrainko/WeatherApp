package com.rk.weatherapp.ui.search.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather

class SearchHistoryViewModel : ViewModel() {

    val cities = MutableLiveData<List<City>>()



}