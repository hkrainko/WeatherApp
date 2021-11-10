package com.rk.weatherapp.infrastructure

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDisplayDate(): String {
//    val calendar = Calendar.getInstance(Locale.ENGLISH)
//    calendar.timeInMillis = this * 1000L
//    return DateFormat.format("HH:mm", calendar).toString()

    val time = this * 1000L
    val date = Date(time)
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getDefault()
    return format.format(date)
}