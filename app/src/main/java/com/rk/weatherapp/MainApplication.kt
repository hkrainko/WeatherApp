package com.rk.weatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.rk.weatherapp.data.repositories.realm.RealmCity
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.InputStream

class MainApplication : Application() {

    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext

        setupRealm()
        Log.d("MainApplication", "MainApplication onCreate")
    }


    private fun setupRealm() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .name("default-realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
//            .inMemory()
            .build()
        Realm.setDefaultConfiguration(config)

        val realm = Realm.getInstance(config)

        // Load realm
        val count = realm.where(RealmCity::class.java).count();
        Log.v("MainApplication", "init realm")
        if (count <= 0) {
            Log.v("MainApplication", "need to load data to realm")
            loadDataToRealm(realm)
        }

        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")
    }

    private fun loadDataToRealm(realm: Realm) {
        val inputStream: InputStream = applicationContext.assets.open("city.list.json.json")

        realm.executeTransaction {
            it.createAllFromJson(RealmCity::class.java, inputStream)
        }
    }
}