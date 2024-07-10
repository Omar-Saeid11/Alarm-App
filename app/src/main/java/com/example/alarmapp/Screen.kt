package com.example.alarmapp

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

sealed class Screen(val route: String) {
    data object AlarmsList : Screen("AlarmsList")
    data object CreateAlarm : Screen("CreateAlarm")
    data object Stopwatch : Screen("Stopwatch")
    data object Timer : Screen("Timer")

    companion object {
        val alarmListDeepLink: List<NavDeepLink> = listOf(
            navDeepLink {
                uriPattern = "https://www.clock.com/AlarmsList"
            },
        )
    }
}
