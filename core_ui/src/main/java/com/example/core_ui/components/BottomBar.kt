package com.example.core_ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

import com.example.core_ui.R


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
    NavigationBar(containerColor = Color(0xFFF3EDF7)) {
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
                        color = Color(0xFF49454F),
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2AE881),
                    indicatorColor = Color(0xFFD4FAE6),
                ),
                onClick = {
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


