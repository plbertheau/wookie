package com.plbertheau.wookiemovies.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Extracts the year from a date string.
 *
 * @param date The date string in the format "yyyy-MM-dd'T'HH:mm:ss".
 * @return The year as a string, or an empty string if the input is invalid.
 */
fun getYear(date: String): String {
    if (date.isBlank()) return ""

    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDate = LocalDate.parse(date,formatter)
        localDate.year.toString()
    } catch (e: DateTimeParseException) {
        ""
    }
}