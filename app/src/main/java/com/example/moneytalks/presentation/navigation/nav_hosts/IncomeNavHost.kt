package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.earnings.EarningsScreen

@Composable
fun IncomeNavHost() {
    val chatNavController = rememberNavController()
    NavHost(chatNavController, startDestination = "доходы") {
        composable("доходы") {
            EarningsScreen()
        }
    }
}


