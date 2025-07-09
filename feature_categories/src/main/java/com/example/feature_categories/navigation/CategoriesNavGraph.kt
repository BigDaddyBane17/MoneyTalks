package com.example.feature_categories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.feature_categories.ui.CategoriesScreen

fun NavGraphBuilder.categoriesNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.CATEGORIES,
        route = Routes.CATEGORIES_GRAPH
    ) {
        composable(Routes.CATEGORIES) {
            CategoriesScreen()
        }
    }
}
