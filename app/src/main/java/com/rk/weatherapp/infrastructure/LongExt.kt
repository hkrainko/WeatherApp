package com.rk.weatherapp.infrastructure

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDisplayHHmm(): String {
    val date = Date(this * 1000L)
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getDefault()
    return format.format(date)
}