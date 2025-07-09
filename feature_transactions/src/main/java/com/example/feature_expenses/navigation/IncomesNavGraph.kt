package com.example.feature_expenses.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddScreen
import com.example.feature_expenses.ui.incomes.incomes_history.IncomesHistoryScreen
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesScreen


//fun NavGraphBuilder.incomesNavGraph(
//    navController: NavHostController
//) {
//    navigation(
//        startDestination = Routes.EARNINGS,
//        route = Routes.EARNINGS_GRAPH
//    ) {
//        composable(Routes.EARNINGS) {
//            IncomesScreen(
//                navigateToHistory = { navController.navigate(Routes.EARNINGS_HISTORY) },
//                navigateToAddTransaction = { navController.navigate(Routes.EARNINGS_ADD) },
//            )
//        }
//        composable(Routes.EARNINGS_HISTORY) {
//            IncomesHistoryScreen(
//                navigateToAnalysis = { navController.navigate(Routes.EARNINGS_ANALYSIS) },
//                onBack = { navController.popBackStack() },
//            )
//        }
//        composable(Routes.EXPENSES_ADD) {
//            IncomesAddScreen(
//                onBack = { navController.popBackStack() }
//            )
//        }
//
//    }
//}