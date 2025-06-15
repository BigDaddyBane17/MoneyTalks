package com.example.moneytalks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneytalks.presentation.account.AccountScreen
import com.example.moneytalks.presentation.create_transaction.CreateTransaction
import com.example.moneytalks.presentation.earnings.EarningsScreen
import com.example.moneytalks.presentation.item_expenses.ItemExpensesScreen
import com.example.moneytalks.presentation.settings.SettingsScreen
import com.example.moneytalks.presentation.spendings.SpendingScreen
import com.example.moneytalks.presentation.history.HistoryScreen

//@Composable
//fun MainNavHost(
//    rootNavController: NavHostController,
//    modifier: Modifier
//) {
//    NavHost(
//        navController = rootNavController,
//        startDestination = "расходы",
//        modifier = modifier
//    ) {
//        composable("расходы") {
//            SpendingScreen(navController = rootNavController)
//        }
//        composable("доходы") {
//            EarningsScreen(navController = rootNavController)
//        }
//        composable("счет") {
//            AccountScreen(navController = rootNavController)
//        }
//        composable("статьи") {
//            ItemExpensesScreen(navController = rootNavController)
//        }
//        composable("настройки") {
//            SettingsScreen(navController = rootNavController)
//        }
//
//        composable("расходы_история") {
//            HistoryScreen(navController = rootNavController)
//        }
//
//
//    }
//}

@Composable
fun TabNavHost(navController: NavHostController, tabRoute: String) {
    when (tabRoute) {
        "расходы" -> NavHost(navController, startDestination = "расходы") {
            composable("расходы") { SpendingScreen(navController = navController) }
            composable("расходы_история") { HistoryScreen(navController = navController) }
            composable("расходы_добавить") { CreateTransaction(navController = navController) }
        }
        "доходы" -> NavHost(navController, startDestination = "доходы") {
            composable("доходы") { EarningsScreen(navController = navController) }

        }
        "счет" -> NavHost(navController, startDestination = "счет") {
            composable("счет") { AccountScreen(navController = navController) }
        }
        "статьи" -> NavHost(navController, startDestination = "статьи") {
            composable("статьи") { ItemExpensesScreen(navController = navController) }
        }
        "настройки" -> NavHost(navController, startDestination = "настройки") {
            composable("настройки") { SettingsScreen(navController = navController) }
        }
    }
}

