package com.rk.weatherapp.testdata

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rk.weatherapp.data.repositories.realm.RealmCity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import java.io.IOException
import java.io.InputStream

class RealmCityTestData {

    companion object {
        suspend fun loadDataToRealm(context: Context, realm: Realm) {
            val inputStream: InputStream = context.assets.open("city.list.json.json")

            realm.executeTransactionAwait {
                it.createAllFromJson(RealmCity::class.java, inputStream)
            }
        }
    }



    // a method to read text file.

}

data class JsonCity(
    val id: String,
    val name: String,
    val state: String,
    val country: String,
    val coord: JsonCoord
)

data class JsonCoord(
    val lon: Double,
    val lat: Double
)
