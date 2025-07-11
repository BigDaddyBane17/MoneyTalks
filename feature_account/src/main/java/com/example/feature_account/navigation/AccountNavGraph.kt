package com.example.feature_account.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.core.di.FeatureComponentProvider
import com.example.feature_account.ui.account_edit.AccountEditScreen
import com.example.feature_account.ui.account_edit.AccountEditViewModel
import com.example.feature_account.ui.account_main.AccountScreen
import com.example.feature_account.ui.account_main.AccountViewModel
import com.example.feature_account.di.DaggerAccountComponent

fun NavGraphBuilder.accountsNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Routes.ACCOUNT,
        route = Routes.ACCOUNTS_GRAPH
    ) {
        composable(Routes.ACCOUNT) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val accountComponent = DaggerAccountComponent.factory().create(featureComponent)
            val viewModelFactory = accountComponent.viewModelFactory()
            val viewModel: AccountViewModel = viewModel(factory = viewModelFactory)
            
            AccountScreen(
                navigateToAccountEdit = { navController.navigate(Routes.ACCOUNT_EDIT) },
                viewModel = viewModel
            )
        }
        composable(Routes.ACCOUNT_EDIT) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val accountComponent = DaggerAccountComponent.factory().create(featureComponent)
            val viewModelFactory = accountComponent.viewModelFactory()
            val viewModel: AccountEditViewModel = viewModel(factory = viewModelFactory)
            
            AccountEditScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
