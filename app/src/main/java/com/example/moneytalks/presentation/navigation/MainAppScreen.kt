package com.example.moneytalks.presentation.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.moneytalks.presentation.analysis.AnalysisTopBar
import com.example.moneytalks.presentation.common.NoTopBar
import com.example.moneytalks.presentation.create_transaction.CreateTransactionTopBar
import com.example.moneytalks.presentation.earnings.EarningsTopBar
import com.example.moneytalks.presentation.history.HistoryTopBar
import com.example.moneytalks.presentation.item_expenses.ItemExpenseTopBar
import com.example.moneytalks.presentation.settings.SettingsTopBar
import com.example.moneytalks.presentation.spendings.SpendingTopBar

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val tabRoutes = listOf(
        "расходы_граф", "доходы_граф", "счет_граф", "статьи_граф", "настройки_граф"
    )

    val selectedTab = tabRoutes.indexOfFirst {
        currentRoute?.startsWith(it.removeSuffix("_граф")) == true
    }.let { if (it == -1) 0 else it }

    val topBarProvider = when (tabRoutes[selectedTab]) {
        "расходы_граф" -> when (currentRoute) {
            "расходы_история" -> HistoryTopBar("расходы")
            "расходы_добавить" -> CreateTransactionTopBar("расходы")
            "расходы_анализ" -> AnalysisTopBar
            else -> SpendingTopBar
        }
        "доходы_граф" -> when (currentRoute) {
            "доходы_история" -> HistoryTopBar("доходы")
            "доходы_добавить" -> CreateTransactionTopBar("доходы")
            "доходы_анализ" -> AnalysisTopBar
            else -> EarningsTopBar
        }
        "счет_граф" -> AccountTopBar
        "статьи_граф" -> ItemExpenseTopBar
        "настройки_граф" -> SettingsTopBar
        else -> NoTopBar
    }

    Scaffold(
        topBar = {
            topBarProvider.provideTopBar(navController)
        },
        bottomBar = {
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
        },
        floatingActionButton = {
            if (currentRoute == "расходы" ||
                currentRoute == "доходы"
            ) {
                FloatingActionButton(
                    onClick = {
                        when {
                            currentRoute.startsWith("расходы") == true -> {
                                navController.navigate("расходы_добавить")
                            }
                            currentRoute.startsWith("доходы") == true -> {
                                navController.navigate("доходы_добавить")
                            }
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
        TabNavHost(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
