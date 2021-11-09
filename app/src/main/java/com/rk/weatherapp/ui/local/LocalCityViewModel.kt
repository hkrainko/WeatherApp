package com.rk.weatherapp.ui.local

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.weatherapp.data.repositories.http.OpenWeatherWeatherRepo
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.interfaces.usecases.WeatherUseCase
import com.rk.weatherapp.domain.usecases.DefaultWeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LocalCityViewModel : ViewModel() {

    private val weatherUseCase: WeatherUseCase = DefaultWeatherUseCase(OpenWeatherWeatherRepo())


    val cityName = MutableLiveData<String>()
    val cityWeather = MutableLiveData<Weather?>()

    fun onResume() {
        CoroutineScope(Dispatchers.IO).async {
            when (val weather = weatherUseCase.getWeatherForCity("1821993")) {
                is Success -> {
//                    cityName.value = weather.value.cityName ?: ""
                    cityWeather.postValue(weather.value)
                }
                is Failure -> {
                    weather.reason
                }
            }
        }
    }

}