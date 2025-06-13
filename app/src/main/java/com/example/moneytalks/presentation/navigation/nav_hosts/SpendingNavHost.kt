package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.spendings.SpendingScreen


@Composable
fun SpendingNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "расходы"
    ) {
        composable("расходы") {
            SpendingScreen()
        }
    }
}
