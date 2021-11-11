package com.rk.weatherapp.infrastructure.database

import android.content.Context
import android.util.Log
import com.rk.weatherapp.data.repositories.realm.RealmCity
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.InputStream

object RealmDBManager {

    lateinit var realm: Realm

    fun setup(context: Context) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .assetFile("pre-populated-realm")
            .name("default-realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
//            .inMemory()
            .build()
        Realm.setDefaultConfiguration(config)

        realm = Realm.getInstance(config)

        // Load realm
//        val count = realm.where(RealmCity::class.java).count();
//        Log.v("MainApplication", "init realm")
//        if (count <= 0) {
//            Log.v("MainApplication", "need to load data to realm")
//            loadDataToRealm(context, realm)
//        }
//
//        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")
    }

//    private fun loadDataToRealm(context: Context, realm: Realm) {
//        val inputStream: InputStream = context.assets.open("city.list.json.json")
//
//        realm.executeTransaction {
//            it.createAllFromJson(RealmCity::class.java, inputStream)
//        }
//    }
}