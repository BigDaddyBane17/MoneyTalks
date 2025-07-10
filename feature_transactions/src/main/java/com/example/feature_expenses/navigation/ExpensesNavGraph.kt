package com.example.feature_expenses.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddScreen
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryScreen
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesScreen
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesViewModel
import com.example.feature_expenses.di.DaggerExpensesComponent
import com.example.core.di.ComponentProvider

fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Routes.EXPENSES,
        route = Routes.EXPENSES_GRAPH
    ) {
        composable(Routes.EXPENSES) { backStackEntry ->
            val context = LocalContext.current
            val componentProvider = context.applicationContext as ComponentProvider
            val applicationComponent = componentProvider.provideApplicationComponent()
            val expensesComponent = DaggerExpensesComponent.factory().create(applicationComponent)
            val viewModelFactory = expensesComponent.viewModelFactory()
            val viewModel: ExpensesViewModel = viewModel(factory = viewModelFactory)
            
            ExpensesScreen(
                navigateToHistory = { navController.navigate(Routes.EXPENSES_HISTORY) },
                navigateToAddTransaction = { navController.navigate(Routes.EXPENSES_ADD) },
                viewModel = viewModel
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
