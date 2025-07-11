package com.example.feature_expenses.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.core.di.FeatureComponentProvider
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddScreen
import com.example.feature_expenses.ui.incomes.incomes_history.IncomesHistoryScreen
import com.example.feature_expenses.ui.incomes.incomes_history.IncomesHistoryViewModel
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesScreen
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesViewModel
import com.example.feature_expenses.di.DaggerIncomesComponent

fun NavGraphBuilder.incomesNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Routes.EARNINGS,
        route = Routes.EARNINGS_GRAPH
    ) {
        composable(Routes.EARNINGS) { backStackEntry ->
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)
            val viewModelFactory = incomesComponent.viewModelFactory()
            val viewModel: IncomesViewModel = viewModel(factory = viewModelFactory)
            
            IncomesScreen(
                navigateToHistory = { navController.navigate(Routes.EARNINGS_HISTORY) },
                navigateToAddTransaction = { navController.navigate(Routes.EARNINGS_ADD) },
                viewModel = viewModel
            )
        }
        composable(Routes.EARNINGS_HISTORY) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            
            // Inject IncomesHistoryViewModel from IncomesComponent
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)
            val incomesViewModelFactory = incomesComponent.viewModelFactory()
            val historyViewModel: IncomesHistoryViewModel = viewModel(factory = incomesViewModelFactory)
            
            IncomesHistoryScreen(
                navigateToAnalysis = { navController.navigate(Routes.EARNINGS_ANALYSIS) },
                onBack = { navController.popBackStack() },
                viewModel = historyViewModel
            )
        }
        composable(Routes.EARNINGS_ADD) {
            IncomesAddScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.EARNINGS_ANALYSIS) {
            // TODO
        }
    }
}