package com.example.hungrybaby

import com.example.hungrybaby.ui.home.checkVolume
import com.example.hungrybaby.ui.home.daysBetween
import com.example.hungrybaby.ui.home.food.checkIfDateIsToday
import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UnitTests {
    @Test
    fun check_past_date_is_not_today_returns_false() {
        val date = "2021-10-10"
        assertEquals(false, checkIfDateIsToday(date))
    }

    @Test
    fun check_today_date_is_today_returns_true() {
        val date = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now())
        assertEquals(true, checkIfDateIsToday(date))
    }

    @ParameterizedTest
    @MethodSource("validVolumes")
    fun check_valid_volume_returns_true(p1: String) {
        val bool = checkVolume(p1)
        assertEquals(true, bool)
    }

    @ParameterizedTest
    @MethodSource("invalidVolumes")
    fun check_invalid_volume_returns_false(p1: String) {
        val bool = checkVolume(p1)
        assertEquals(false, bool)
    }

    @Test
    fun check_days_in_between() {
        val date1 = LocalDate.parse("2021-10-12")
        val date2 = LocalDate.parse("2021-10-10")
        val days = daysBetween(date1, date2)
        assertEquals(2, days)
    }

    companion object {
        @JvmStatic
        fun validVolumes() =
            listOf(
                Arguments.of("1"),
                Arguments.of("2"),
                Arguments.of("500"),
            )

        @JvmStatic
        fun invalidVolumes() =
            listOf(
                Arguments.of("0"),
                Arguments.of("-1"),
                Arguments.of("abc"),
            )
    }
}
