package com.example.feature_expenses.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddScreen
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryScreen
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesScreen

fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Routes.EXPENSES,
        route = Routes.EXPENSES_GRAPH
    ) {
        composable(Routes.EXPENSES) {
            ExpensesScreen(
                navigateToHistory = { navController.navigate(Routes.EXPENSES_HISTORY) },
                navigateToAddTransaction = { navController.navigate(Routes.EXPENSES_ADD) },
            )
        }
        composable(Routes.EXPENSES_HISTORY) {
            ExpensesHistoryScreen(
                navigateToAnalysis = { navController.navigate(Routes.EXPENSES_ANALYSIS) },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(Routes.EXPENSES_ADD) {
            ExpensesAddScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.EXPENSES_ANALYSIS) {
            // TODO
        }
    }
}
