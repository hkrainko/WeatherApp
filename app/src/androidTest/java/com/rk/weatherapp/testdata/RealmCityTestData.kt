package com.rk.weatherapp.testdata

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rk.weatherapp.data.repositories.realm.RealmCity
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.IOException
import java.io.InputStream

class RealmCityTestData {

    companion object {
        fun loadRealmDataFromFile(context: Context) {

            loadRealm(context, "city.list.json.json")
//            val jsonString = loadJsonToFromFile(context, "city.list.json.json")
//            Log.v("RealmCityTestData", "jsonString:${jsonString?.length}")
//            val gson = Gson()
//            try {
////                val obj = gson.fromJson()
//
//
//
////                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
//            } catch (ioException: IOException) {
//                ioException.printStackTrace()
//                return null
//            }
//            return jsonString
        }

        fun loadJsonToFromFile(context: Context, fileName: String): String? {
            var json: String? = null
            json = try {
                val inputStream: InputStream = context.assets.open(fileName)
//                val inputStream: InputStream = context.resources.openRawResource(R.city_list_json)
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, charset("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }

        private fun loadRealm(context: Context, fileName: String) {
            val config = RealmConfiguration.Builder()
                .name("conv-realm")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .compactOnLaunch()
//            .inMemory()
                .build()
            Realm.setDefaultConfiguration(config)

            val inputStream: InputStream = context.assets.open(fileName)

            val realm = Realm.getInstance(config)

            realm.executeTransaction {
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
