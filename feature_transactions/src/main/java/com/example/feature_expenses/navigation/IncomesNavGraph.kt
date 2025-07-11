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
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesScreen
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesViewModel
import com.example.feature_expenses.ui.history.HistoryViewModel
import com.example.feature_expenses.di.DaggerIncomesComponent
import com.example.feature_expenses.di.DaggerHistoryComponent
import com.example.feature_account.di.DaggerAccountComponent
import com.example.feature_account.ui.account_main.AccountViewModel

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
            
            // Inject HistoryViewModel
            val historyComponent = DaggerHistoryComponent.factory().create(featureComponent)
            val historyViewModelFactory = historyComponent.viewModelFactory()
            val historyViewModel: HistoryViewModel = viewModel(factory = historyViewModelFactory)
            
            // Inject AccountViewModel
            val accountComponent = DaggerAccountComponent.factory().create(featureComponent)
            val accountViewModelFactory = accountComponent.viewModelFactory()
            val accountViewModel: AccountViewModel = viewModel(factory = accountViewModelFactory)
            
            IncomesHistoryScreen(
                navigateToAnalysis = { navController.navigate(Routes.EARNINGS_ANALYSIS) },
                onBack = { navController.popBackStack() },
                viewModel = historyViewModel,
                accountViewModel = accountViewModel,
                accountId = null // Will be retrieved reactively from accountViewModel
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