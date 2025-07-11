package com.example.feature_expenses.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.core.navigation.Routes
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddScreen
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddViewModel
import com.example.feature_expenses.ui.expenses.expenses_edit.ExpensesEditScreen
import com.example.feature_expenses.ui.expenses.expenses_edit.ExpensesEditViewModel
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryScreen
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryViewModel
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesScreen
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesViewModel
import com.example.feature_expenses.di.DaggerExpensesComponent
import com.example.core.di.FeatureComponentProvider

fun NavGraphBuilder.expensesNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Routes.EXPENSES,
        route = Routes.EXPENSES_GRAPH
    ) {
        composable(Routes.EXPENSES) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val expensesComponent = DaggerExpensesComponent.factory().create(featureComponent)
            val viewModelFactory = expensesComponent.viewModelFactory()
            val viewModel: ExpensesViewModel = viewModel(factory = viewModelFactory)
            
            ExpensesScreen(
                navigateToHistory = { navController.navigate(Routes.EXPENSES_HISTORY) },
                navigateToAddTransaction = { navController.navigate(Routes.EXPENSES_ADD) },
                navigateToEditTransaction = { transactionId -> 
                    navController.navigate(Routes.expensesEdit(transactionId))
                },
                viewModel = viewModel
            )
        }
        
        composable(Routes.EXPENSES_ADD) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val expensesComponent = DaggerExpensesComponent.factory().create(featureComponent)
            val viewModelFactory = expensesComponent.viewModelFactory()
            val viewModel: ExpensesAddViewModel = viewModel(factory = viewModelFactory)
            
            ExpensesAddScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        
        composable(
            route = Routes.EXPENSES_EDIT,
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: 0
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val expensesComponent = DaggerExpensesComponent.factory().create(featureComponent)

            val viewModel = ExpensesEditViewModel(
                transactionId = transactionId,
                getTransactionByIdUseCase = expensesComponent.getTransactionByIdUseCase(),
                updateTransactionUseCase = expensesComponent.updateTransactionUseCase(),
                deleteTransactionUseCase = expensesComponent.deleteTransactionUseCase(),
                accountRepository = expensesComponent.accountRepository(),
                categoryRepository = expensesComponent.categoryRepository()
            )
            
            ExpensesEditScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        
        composable(Routes.EXPENSES_HISTORY) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val expensesComponent = DaggerExpensesComponent.factory().create(featureComponent)
            val viewModelFactory = expensesComponent.viewModelFactory()
            val viewModel: ExpensesHistoryViewModel = viewModel(factory = viewModelFactory)
            
            ExpensesHistoryScreen(
                navigateToAnalysis = { /* TODO: Implement analysis navigation */ },
                navigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
