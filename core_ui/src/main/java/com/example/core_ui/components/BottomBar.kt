package com.example.core_ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.core.di.ComponentProvider
import com.example.core.di.FeatureComponentProvider
import com.example.core.utils.HapticUtils
import com.example.core.models.HapticMode
import com.example.core_ui.R
import androidx.compose.material3.MaterialTheme
import com.example.core_ui.theme.ThemeProvider

data class BottomNavigationItem(
    val title: String,
    val icon: Int
)

val items = listOf(
    BottomNavigationItem("Расходы", R.drawable.spendings),
    BottomNavigationItem("Доходы", R.drawable.earnings),
    BottomNavigationItem("Счет", R.drawable.account),
    BottomNavigationItem("Статьи", R.drawable.articles),
    BottomNavigationItem("Настройки", R.drawable.settings),
)

@Composable
fun BottomBar(
    tabRoutes: List<String>,
    selectedTab: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as FeatureComponentProvider).provideFeatureComponent()
    val settingsPreferences = appComponent.settingsPreferences()
    
    val hapticEnabled by settingsPreferences.hapticEnabled.collectAsState(initial = true)
    val hapticMode by settingsPreferences.hapticMode.collectAsState(initial = HapticMode.MEDIUM.name)
    val appThemeId by settingsPreferences.appThemeId.collectAsState(initial = "default")
    
    // Получаем выбранную тему для цветов
    val selectedTheme = ThemeProvider.getThemeById(appThemeId)
    
    NavigationBar(
        containerColor = selectedTheme.primaryColor.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTab == index,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedTab == index) {
                            selectedTheme.primaryColor
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        },
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedTheme.primaryColor,
                    indicatorColor = selectedTheme.primaryColor.copy(alpha = 0.2f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                onClick = {
                    // Выполняем хаптик при переключении таба
                    if (hapticEnabled) {
                        HapticUtils.performHapticFeedback(context, HapticMode.valueOf(hapticMode))
                    }
                    
                    val tabRoute = tabRoutes[index]
                    navController.navigate(tabRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


