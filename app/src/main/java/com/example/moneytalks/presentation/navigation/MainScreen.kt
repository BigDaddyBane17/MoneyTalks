package com.example.moneytalks.presentation.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.account.AccountTopBar
import com.example.moneytalks.presentation.common.NoTopBar
import com.example.moneytalks.presentation.earnings.EarningsTopBar
import com.example.moneytalks.presentation.item_expenses.ItemExpenseTopBar
import com.example.moneytalks.presentation.settings.SettingsTopBar
import com.example.moneytalks.presentation.spendings.SpendingTopBar


@Composable
fun MainAppScreen() {
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarProvider = when(currentRoute) {
        "расходы" -> SpendingTopBar
        "доходы" -> EarningsTopBar
        "счет" -> AccountTopBar
        "статьи" -> ItemExpenseTopBar
        "настройки" -> SettingsTopBar
        else -> NoTopBar
    }

    Scaffold(
        topBar = {
            topBarProvider.provideTopBar()
        },

        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFFF3EDF7)
            ) {
                items.forEach { item ->
                    val isSelected = item.title.lowercase() == navBackStackEntry?.destination?.route
                    NavigationBarItem(
                        selected = isSelected,
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier
                                    .size(24.dp)
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
                            rootNavController.navigate(item.title.lowercase()) {
                                popUpTo(rootNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentRoute == "расходы" || currentRoute == "доходы" || currentRoute == "счет") {
                FloatingActionButton(
                    onClick = {
                        when(currentRoute) {
                            "расходы" -> {}
                            "доходы" -> {}
                            "счет" -> {}
                        }
                    },
                    containerColor = Color(0xFF2AE881),
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        containerColor = Color(0xFFFef7ff)
    ) { padding ->
        MainNavHost(rootNavController, modifier = Modifier
            .padding(padding))
    }
}