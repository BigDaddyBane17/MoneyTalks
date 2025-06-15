package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.earnings.EarningsScreen

@Composable
fun IncomeNavHost() {
    val incomeNavController = rememberNavController()
    NavHost(incomeNavController, startDestination = "доходы") {
        composable("доходы") {
        }

    }
}


