package com.rk.weatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.rk.weatherapp.data.repositories.realm.RealmCity
import com.rk.weatherapp.infrastructure.database.RealmDBManager
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

        RealmDBManager.setup(applicationContext)
        Log.d("MainApplication", "MainApplication onCreate")
    }
}