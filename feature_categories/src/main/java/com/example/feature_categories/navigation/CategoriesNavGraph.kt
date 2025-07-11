package com.example.feature_categories.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.core.di.FeatureComponentProvider
import com.example.feature_categories.ui.CategoriesScreen
import com.example.feature_categories.ui.CategoryViewModel
import com.example.feature_categories.di.DaggerCategoriesComponent

fun NavGraphBuilder.categoriesNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Routes.CATEGORIES,
        route = Routes.CATEGORIES_GRAPH
    ) {
        composable(Routes.CATEGORIES) {
            val context = LocalContext.current
            val featureComponentProvider = context.applicationContext as FeatureComponentProvider
            val featureComponent = featureComponentProvider.provideFeatureComponent()
            val categoriesComponent = DaggerCategoriesComponent.factory().create(featureComponent)
            val viewModelFactory = categoriesComponent.viewModelFactory()
            val viewModel: CategoryViewModel = viewModel(factory = viewModelFactory)
            
            CategoriesScreen(
                viewModel = viewModel
            )
        }
    }
}
