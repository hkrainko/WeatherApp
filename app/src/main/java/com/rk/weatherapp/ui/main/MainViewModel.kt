package com.rk.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.weatherapp.data.repositories.realm.RealmCityRepo
import com.rk.weatherapp.domain.entities.City
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.interfaces.usecases.SearchHistoryUseCase
import com.rk.weatherapp.domain.usecases.DefaultSearchHistoryUseCase
import com.rk.weatherapp.infrastructure.database.RealmDBManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    private val searchHistoryUseCase: SearchHistoryUseCase =
        DefaultSearchHistoryUseCase(RealmCityRepo(RealmDBManager.realm))

    private val cities: MutableLiveData<List<City>> by lazy {
        MutableLiveData<List<City>>().also {
            loadUsers()
        }
    }

    fun getCities(): LiveData<List<City>> {
        return cities
    }

    // UI events
    fun onQueryTextChange(q: String) {
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
                }
                is Failure -> {
                    Log.d("MainViewModel", "Failure:${result.reason}")
                }
            }
        }
    }


    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

}