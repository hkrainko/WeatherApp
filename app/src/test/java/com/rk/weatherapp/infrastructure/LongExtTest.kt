package com.rk.weatherapp.infrastructure

import org.junit.Assert.*
import org.junit.Test

class LongExtTest {
    @Test
    fun toDisplayHHmm_isCorrect() {
        val unixTimestampValue = 1636650552L
        assertEquals("01:09", unixTimestampValue.toDisplayHHmm())
    }
}