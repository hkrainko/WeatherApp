package com.rk.weatherapp.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Weather

class CityViewModel : ViewModel() {

    val displayCity = MutableLiveData<City?>()

    val isLocal = MutableLiveData(true)

    var localCity: City? = null

    fun updateLocalCity(city: City) {
        this.localCity = city
        if (displayCity.value == null || displayCity.value?.id == city.id) {
            this.displayCity.postValue(city)
            this.isLocal.postValue(true)
        }
    }

    fun displayQueryCity(city: City?) {
        this.displayCity.postValue(city)
        this.isLocal.postValue(false)
    }

    fun updateWeather(weather: Weather?) {
        if (weather == null) {
            return
        }
        if (displayCity.value?.id == weather.cityId) {
            val copyOfCity = displayCity.value
            copyOfCity?.weather = weather
            displayCity.postValue(copyOfCity)
        }
        if (localCity?.id == weather.cityId) {
            if (localCity != null) {
                val copyOfCity = localCity
                copyOfCity?.weather = weather
                localCity = copyOfCity
            }
        }
    }

    // UI events
    fun onClickBackButton() {
        this.displayCity.postValue(localCity)
        this.isLocal.postValue(true)
    }
}

//class LocalCityViewModelFactory constructor(private val weather: Weather?) :
//    ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return with(modelClass) {
//            when {
//                isAssignableFrom(LocalCityViewModel::class.java) ->
//                    LocalCityViewModel()
//                else ->
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//    }
//}