package com.example.feature_settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Routes
import com.example.feature_settings.ui.SettingsScreen
import com.example.feature_settings.ui.about.AboutScreen
import com.example.feature_settings.ui.haptics.HapticsScreen
import com.example.feature_settings.ui.language.LanguageScreen
import com.example.feature_settings.ui.pincode.PinCodeScreen
import com.example.feature_settings.ui.sync.SyncScreen
import com.example.feature_settings.ui.theme.ThemeScreen

fun NavGraphBuilder.settingsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Routes.SETTINGS,
        route = Routes.SETTINGS_GRAPH
    ) {
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onNavigateToTheme = { navController.navigate("theme") },
                onNavigateToHaptics = { navController.navigate("haptics") },
                onNavigateToPinCode = { navController.navigate("pin_code") },
                onNavigateToSync = { navController.navigate("sync") },
                onNavigateToLanguage = { navController.navigate("language") },
                onNavigateToAbout = { navController.navigate("about") }
            )
        }
        
        composable("theme") {
            ThemeScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("haptics") {
            HapticsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("pin_code") {
            PinCodeScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("sync") {
            SyncScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("language") {
            LanguageScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("about") {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
