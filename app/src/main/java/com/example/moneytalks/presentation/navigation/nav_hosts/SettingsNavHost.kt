package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.settings.SettingsScreen


@Composable
fun SettingsNavHost() {
    val settingsNavController = rememberNavController()
    NavHost(settingsNavController, startDestination = "настройки") {
        composable("настройки") {
            SettingsScreen(
            )
        }
    }
}