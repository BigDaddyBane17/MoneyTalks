package com.example.moneytalks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.navigation.Routes
import com.example.feature_account.navigation.accountsNavGraph
import com.example.feature_categories.navigation.categoriesNavGraph
import com.example.feature_expenses.navigation.expensesNavGraph
import com.example.feature_expenses.navigation.incomesNavGraph
import com.example.feature_settings.navigation.settingsNavGraph

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.EXPENSES_GRAPH,
        modifier = modifier
    ) {
        expensesNavGraph(navController)
        incomesNavGraph(navController)
        accountsNavGraph(navController)
        categoriesNavGraph(navController)
        settingsNavGraph(navController)
    }
}
