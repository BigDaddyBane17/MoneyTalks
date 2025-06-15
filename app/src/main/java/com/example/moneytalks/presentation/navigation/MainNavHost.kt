package com.example.moneytalks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moneytalks.presentation.account.AccountScreen
import com.example.moneytalks.presentation.analysis.AnalysisScreen
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

//@Composable
//fun TabNavHost(navController: NavHostController, tabRoute: String) {
//    when (tabRoute) {
//        "расходы" -> NavHost(navController, startDestination = "расходы") {
//            composable("расходы") { SpendingScreen(navController = navController) }
//            composable("расходы_история") { HistoryScreen(navController = navController) }
//            composable("расходы_добавить") { CreateTransaction(navController = navController) }
//        }
//        "доходы" -> NavHost(navController, startDestination = "доходы") {
//            composable("доходы") { EarningsScreen(navController = navController) }
//
//        }
//        "счет" -> NavHost(navController, startDestination = "счет") {
//            composable("счет") { AccountScreen(navController = navController) }
//        }
//        "статьи" -> NavHost(navController, startDestination = "статьи") {
//            composable("статьи") { ItemExpensesScreen(navController = navController) }
//        }
//        "настройки" -> NavHost(navController, startDestination = "настройки") {
//            composable("настройки") { SettingsScreen(navController = navController) }
//        }
//    }
//}
//

@Composable
fun TabNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "расходы_граф"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Граф "Расходы"
        navigation(startDestination = "расходы", route = "расходы_граф") {
            composable("расходы") { SpendingScreen(navController = navController) }
            composable("расходы_история") { HistoryScreen(navController = navController, type = "расходы") }
            composable("расходы_добавить") { CreateTransaction(navController = navController, type = "расходы") }
            composable("расходы_анализ") { AnalysisScreen(navController = navController, type = "расходы") }
        }
        // Граф "Доходы"
        navigation(startDestination = "доходы", route = "доходы_граф") {
            composable("доходы") { EarningsScreen(navController = navController) }
            composable("доходы_история") { HistoryScreen(navController = navController, type = "доходы") }
            composable("доходы_добавить") { CreateTransaction(navController = navController, type = "доходы") }
            composable("доходы_анализ") { AnalysisScreen(navController = navController, type = "доходы") }
        }
        // Граф "Счет"
        navigation(startDestination = "счет", route = "счет_граф") {
            composable("счет") { AccountScreen(navController = navController) }
            composable("счет_добавить") { CreateTransaction(navController = navController, type = "счет") }
        }
        // Граф "Статьи"
        navigation(startDestination = "статьи", route = "статьи_граф") {
            composable("статьи") { ItemExpensesScreen(navController = navController) }
        }
        // Граф "Настройки"
        navigation(startDestination = "настройки", route = "настройки_граф") {
            composable("настройки") { SettingsScreen(navController = navController) }
        }
    }
}
