package com.rk.weatherapp.data.repositories.http

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenWeatherWeatherRepoTest {


    private lateinit var weatherRepo: WeatherRepo

    @Before
    fun setUp() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        weatherRepo = OpenWeatherWeatherRepo()
    }

    @After
    fun tearDown() {

    }


    @Test
    fun getWeatherByCityId_isCorrect(): Unit = runBlocking {

        val result = weatherRepo.getWeatherByCityId(1821993)

        when (result) {
            is Success -> {
                Log.v("Test", "Success: ${result.value}")
                Assert.assertNotNull(result.value.condition)
            }
            is Failure -> {
                Log.v("Test", "Failure: ${result.reason}")
                Assert.fail()
            }
        }
    }
}