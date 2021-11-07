package com.rk.weatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

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
        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")
    }

    private fun loadRealmFile() {

    }
}