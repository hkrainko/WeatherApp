package com.rk.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.domain.entities.City

class MainViewModel : ViewModel() {

    private val cities: MutableLiveData<List<City>> by lazy {
        MutableLiveData<List<City>>().also {
            loadUsers()
        }
    }

    fun getCities(): LiveData<List<City>> {
        return cities
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

}