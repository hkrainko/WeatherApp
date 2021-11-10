package com.rk.weatherapp.ui.local

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rk.weatherapp.domain.entities.Weather

class LocalCityViewModel(weather: Weather?) : ViewModel() {

    val cityWeather = MutableLiveData<Weather?>()

    init {
        cityWeather.value = weather
    }


}

class LocalCityViewModelFactory constructor(private val weather: Weather?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(LocalCityViewModel::class.java) ->
                    LocalCityViewModel(weather)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}