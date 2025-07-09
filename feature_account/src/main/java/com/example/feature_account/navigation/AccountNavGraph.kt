package com.example.feature_account.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.feature_account.ui.account_edit.AccountEditScreen
import com.example.feature_account.ui.account_main.AccountScreen

fun NavGraphBuilder.accountsNavGraph(
    navController: NavHostController,
) {
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
            AccountEditScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
