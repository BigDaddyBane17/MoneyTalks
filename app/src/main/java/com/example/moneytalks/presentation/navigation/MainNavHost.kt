package com.example.moneytalks.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneytalks.presentation.navigation.nav_hosts.AccountNavHost
import com.example.moneytalks.presentation.navigation.nav_hosts.ExpensesNavHost
import com.example.moneytalks.presentation.navigation.nav_hosts.IncomeNavHost
import com.example.moneytalks.presentation.navigation.nav_hosts.SettingsNavHost
import com.example.moneytalks.presentation.navigation.nav_hosts.SpendingNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    rootNavController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = rootNavController,
        startDestination = "расходы",
        modifier = modifier
    ) {
        composable("расходы") {
            SpendingNavHost()
        }
        composable("доходы") {
            IncomeNavHost()
        }
        composable("счет") {
            AccountNavHost()
        }
        composable("статьи") {
            ExpensesNavHost()
        }
        composable("настройки") {
            SettingsNavHost()
        }
    }

}

