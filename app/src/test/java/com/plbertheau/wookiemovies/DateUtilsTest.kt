package com.plbertheau.wookiemovies

import com.plbertheau.wookiemovies.utils.getYear
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {

    @Test
    fun testGetYear_validDate() {
        assertEquals("2023", getYear("2023-10-26T12:34:56"))
    }

    @Test
    fun testGetYear_emptyDate() {
        assertEquals("", getYear(""))}

    @Test
    fun testGetYear_nullDate() {
        assertEquals("", getYear(" "))
    }

    @Test
    fun testGetYear_invalidDate() {
        assertEquals("", getYear("2023/10/26 12:34:56"))
    }
}