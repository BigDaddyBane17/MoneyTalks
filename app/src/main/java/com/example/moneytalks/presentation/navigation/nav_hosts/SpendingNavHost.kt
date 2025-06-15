package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun SpendingNavHost() {
    val spendingNavController = rememberNavController()
    NavHost(
        navController = spendingNavController,
        startDestination = "расходы"
    ) {
        composable("расходы") {
        }

    }
}
