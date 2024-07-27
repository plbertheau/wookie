package com.plbertheau.wookiemovies.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getYear(date: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val yourDate: Date? = sdf.parse(date)
    val calendar: Calendar = Calendar.getInstance()
    if (yourDate != null) {
        calendar.time = yourDate
    }
    val year = calendar.get(Calendar.YEAR)
    return year.toString()
}