package com.rk.weatherapp.data.repositories.realm

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rk.weatherapp.domain.entities.Failure
import com.rk.weatherapp.domain.entities.Success
import com.rk.weatherapp.domain.interfaces.repositories.CityRepo
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RealmCityRepoTest {

    private lateinit var realm: Realm

    private lateinit var cityRepo: CityRepo

    @Before
    fun setUp() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Realm.init(appContext)
        val config = RealmConfiguration.Builder()
            .name("test-realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
//            .inMemory()
            .build()
//        Realm.setDefaultConfiguration(config)

        realm = Realm.getInstance(config)

        cityRepo = RealmCityRepo(realm)

        insertMockData()

    }

    @After
    fun tearDown() {

    }


    @Test
    fun getCitiesByName_isCorrect() = runBlocking {

        val result = cityRepo.getCitiesByName("na")

        when (result) {
            is Success -> {
                Log.v("Test", "Success: ${result.value}")
                Assert.assertNotEquals(0, result.value.size)
            }
            is Failure -> {
                Log.v("Test", "Failure: ${result.reason}")
                Assert.fail()
            }
        }

        Log.v("Test", "123")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        Assert.assertEquals("com.rk.weatherapp", appContext.packageName)
    }


    private suspend fun insertMockData() {
        realm.executeTransactionAwait { r: Realm ->

            val city = RealmCity(
                "1",
                "nameEn",
                "nameZh",
                "country",
                1.0,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0,
                7.0,
                8.0,
                1636239003,
                1636281055,
                "conditionDesc",
                RealmWeatherConditionType.Thunderstorm.desc,
                1636239003,
                1636239003
            )

            r.insertOrUpdate(city)
        }


//        realm.executeTransaction { r: Realm ->
//
//
//
//            // Instantiate the class using the factory function.
//            val turtle = r.createObject(RealmCity::class.java, ObjectId())
//            // Configure the instance.
//            turtle.name = "Max"
//            // Create a TurtleEnthusiast with a primary key.
//            val primaryKeyValue = ObjectId()
//            val turtleEnthusiast = r.createObject(
//                TurtleEnthusiast::class.java,
//                primaryKeyValue
//            )
//        }
    }
}