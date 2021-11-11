package com.rk.weatherapp.ui.local

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather

class LocalCityViewModel(weather: Weather?) : ViewModel() {

    val city = MutableLiveData<City?>()

    init {
//        city.value = weather
    }

    fun updateCity(city: City?) {
        this.city.postValue(city)
    }

    fun updateWeather(weather: Weather?) {
        if (weather == null) {
            return
        }
        if (city.value?.id != weather.cityId) {
            return
        }
        val copyOfCity = city.value
        copyOfCity?.weather = weather
        city.postValue(copyOfCity)
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