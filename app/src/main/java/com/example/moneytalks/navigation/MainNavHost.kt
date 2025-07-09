package com.example.moneytalks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.navigation.Routes
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.feature_account.ui.account_edit.AccountEditScreen
import com.example.feature_account.ui.account_main.AccountScreen
import com.example.feature_categories.ui.CategoriesScreen
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddScreen
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryScreen
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesScreen
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddScreen
import com.example.feature_expenses.ui.incomes.incomes_history.IncomesHistoryScreen
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesScreen
import com.example.feature_settings.ui.SettingsScreen

@Composable
fun Test() {}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.EXPENSES_GRAPH
    ) {
        // Граф "Расходы"
        navigation(
            startDestination = Routes.EXPENSES,
            route = Routes.EXPENSES_GRAPH
        ) {
            composable(Routes.EXPENSES) {
                ExpensesScreen(
                    navigateToHistory = { navController.navigate(Routes.EXPENSES_HISTORY) },
                    navigateToAddTransaction = { navController.navigate(Routes.EXPENSES_ADD) }
                )
            }
            composable(Routes.EXPENSES_HISTORY) {
                ExpensesHistoryScreen(
                    navigateToAnalysis = { navController.navigate(Routes.EXPENSES_ANALYSIS) },
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable(Routes.EXPENSES_ADD) {
                ExpensesAddScreen(onBack = { navController.popBackStack() })
            }

        }
        // Граф "Доходы"
        navigation(
            startDestination = Routes.EARNINGS,
            route = Routes.EARNINGS_GRAPH
        ) {
            composable(Routes.EARNINGS) {
                IncomesScreen(
                    navigateToHistory = { navController.navigate(Routes.EARNINGS_HISTORY) },
                    navigateToAddTransaction = { navController.navigate(Routes.EARNINGS_ADD) }
                )
            }
            composable(Routes.EARNINGS_HISTORY) {
                IncomesHistoryScreen(
                    navigateToAnalysis = { navController.navigate(Routes.EARNINGS_ANALYSIS) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.EARNINGS_ADD) {
                IncomesAddScreen(onBack = { navController.popBackStack() })
            }

        }
        // Граф "Счет"
        navigation(
            startDestination = Routes.ACCOUNT,
            route = Routes.ACCOUNTS_GRAPH
        ) {
            composable(Routes.ACCOUNT) {
                AccountScreen(
                    navigateToAccountEdit = { navController.navigate(Routes.ACCOUNT_EDIT) }
                )
            }
            composable(Routes.ACCOUNT_EDIT) {
                AccountEditScreen(onBack = { navController.popBackStack() })
            }
        }
        // Граф "Статьи"
        navigation(
            startDestination = Routes.CATEGORIES,
            route = Routes.CATEGORIES_GRAPH
        ) {
            composable(Routes.CATEGORIES) {
                CategoriesScreen()
            }
        }
        // Граф "Настройки"
        navigation(
            startDestination = Routes.SETTINGS,
            route = Routes.SETTINGS_GRAPH
        ) {
            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
