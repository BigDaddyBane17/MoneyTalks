package com.example.moneytalks.presentation.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.moneytalks.presentation.create_transaction.CreateTransactionTopBar
import com.example.moneytalks.presentation.earnings.EarningsTopBar
import com.example.moneytalks.presentation.history.HistoryScreen
import com.example.moneytalks.presentation.history.HistoryTopBar
import com.example.moneytalks.presentation.item_expenses.ItemExpenseTopBar
import com.example.moneytalks.presentation.settings.SettingsTopBar
import com.example.moneytalks.presentation.spendings.SpendingTopBar

@Composable
fun MainAppScreen() {
    val navControllers = tabRoutes.map { rememberNavController() }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    var tabHistory by rememberSaveable { mutableStateOf(listOf(0)) }

    LaunchedEffect(selectedTab) {
        if (tabHistory.lastOrNull() != selectedTab) {
            tabHistory = tabHistory + selectedTab
        }
    }

    val currentNavController = navControllers[selectedTab]
    val currentBackStackEntry by currentNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    BackHandler {
        if (currentNavController.previousBackStackEntry != null) {
            currentNavController.popBackStack()
        }
        else if (tabHistory.size > 1) {
            tabHistory = tabHistory.dropLast(1)
            selectedTab = tabHistory.last()
        }

    }

    val topBarProvider = when (tabRoutes[selectedTab]) {
        "расходы" -> when (currentRoute) {
            "расходы_история" -> HistoryTopBar
            "расходы_добавить" -> CreateTransactionTopBar
            else -> SpendingTopBar
        }
        "доходы" -> EarningsTopBar
        "счет" -> AccountTopBar
        "статьи" -> ItemExpenseTopBar
        "настройки" -> SettingsTopBar
        else -> NoTopBar
    }

    Scaffold(
        topBar = {
            topBarProvider.provideTopBar(currentNavController)
        },


        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFF3EDF7)
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
                                color = Color(0xFF49454F),
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2AE881),
                            indicatorColor = Color(0xFFD4FAE6),
                        ),
                        onClick = { selectedTab = index }
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentRoute == "расходы" || currentRoute == "доходы" || currentRoute == "счет") {
                FloatingActionButton(
                    onClick = {
                        when(currentRoute) {
                            "расходы" -> {
                                currentNavController.navigate("расходы_добавить")
                            }
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
        Box(Modifier.padding(padding)) {
            tabRoutes.forEachIndexed { index, tabRoute ->
                TabNavHost(navController = navControllers[selectedTab], tabRoute = tabRoutes[selectedTab])
            }
        }
    }
}