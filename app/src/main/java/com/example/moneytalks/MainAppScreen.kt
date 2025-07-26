package com.example.moneytalks
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.navigation.MainNavHost
import com.example.core.navigation.Routes
import com.example.core_ui.components.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    
    // Улучшенная логика определения выбранного таба
    val selectedTab = when {
        currentRoute?.startsWith("расходы") == true -> 0
        currentRoute?.startsWith("доходы") == true -> 1
        currentRoute?.startsWith("счет") == true -> 2
        currentRoute?.startsWith("статьи") == true -> 3
        currentRoute?.startsWith("настройки") == true || 
        currentRoute?.startsWith("theme") == true ||
        currentRoute?.startsWith("haptics") == true ||
        currentRoute?.startsWith("pin_code") == true ||
        currentRoute?.startsWith("sync") == true ||
        currentRoute?.startsWith("language") == true ||
        currentRoute?.startsWith("about") == true -> 4
        else -> 0 // По умолчанию первый таб
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                tabRoutes = tabRoutes,
                navController = navController,
                selectedTab = selectedTab,
            )
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}



