package com.example.feature_expenses.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.core.navigation.Routes
import com.example.core.di.FeatureComponentProvider
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddScreen
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddViewModel
import com.example.feature_expenses.ui.incomes.incomes_edit.IncomesEditScreen
import com.example.feature_expenses.ui.incomes.incomes_edit.IncomesEditViewModel
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
        composable(Routes.EARNINGS) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)
            val viewModelFactory = incomesComponent.viewModelFactory()
            val viewModel: IncomesViewModel = viewModel(factory = viewModelFactory)
            
            IncomesScreen(
                navigateToHistory = { navController.navigate(Routes.EARNINGS_HISTORY) },
                navigateToAddTransaction = { navController.navigate(Routes.EARNINGS_ADD) },
                navigateToEditTransaction = { transactionId -> 
                    navController.navigate(Routes.earningsEdit(transactionId))
                },
                viewModel = viewModel
            )
        }

        composable(Routes.EARNINGS_ADD) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)
            val viewModelFactory = incomesComponent.viewModelFactory()
            val viewModel: IncomesAddViewModel = viewModel(factory = viewModelFactory)
            
            IncomesAddScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        
        composable(
            route = Routes.EARNINGS_EDIT,
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: 0
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)

            val viewModel = IncomesEditViewModel(
                transactionId = transactionId,
                getTransactionByIdUseCase = incomesComponent.getTransactionByIdUseCase(),
                updateTransactionUseCase = incomesComponent.updateTransactionUseCase(),
                deleteTransactionUseCase = incomesComponent.deleteTransactionUseCase(),
                accountRepository = incomesComponent.accountRepository(),
                categoryRepository = incomesComponent.categoryRepository()
            )
            
            IncomesEditScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(Routes.EARNINGS_HISTORY) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val incomesComponent = DaggerIncomesComponent.factory().create(featureComponent)
            val viewModelFactory = incomesComponent.viewModelFactory()
            val viewModel: IncomesHistoryViewModel = viewModel(factory = viewModelFactory)
            
            IncomesHistoryScreen(
                navigateToAnalysis = { /* TODO: Implement analysis navigation */ },
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}