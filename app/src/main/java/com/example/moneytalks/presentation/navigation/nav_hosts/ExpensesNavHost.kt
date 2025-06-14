package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.item_expenses.ItemExpensesScreen

@Composable
fun ExpensesNavHost() {
    val settingsNavController = rememberNavController()
    NavHost(settingsNavController, startDestination = "статьи") {
        composable("статьи") {
            ItemExpensesScreen()
        }
    }
}

