package com.example.moneytalks
import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.navigation.MainNavHost
import com.example.core.navigation.Routes
import com.example.core_ui.components.BottomBar


@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val tabRoutes = listOf(
        Routes.EXPENSES_GRAPH,
        Routes.EARNINGS_GRAPH,
        Routes.ACCOUNTS_GRAPH,
        Routes.CATEGORIES_GRAPH,
        Routes.SETTINGS_GRAPH
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val selectedTab = tabRoutes.indexOfFirst { currentRoute?.startsWith(it) == true }
        .let { if (it == -1) 0 else it }

    Scaffold(
        bottomBar = {
            BottomBar(
                tabRoutes = tabRoutes,
                navController = navController,
                selectedTab = selectedTab,
            )
        }
    ) {
        MainNavHost(navController = navController)
    }
}



