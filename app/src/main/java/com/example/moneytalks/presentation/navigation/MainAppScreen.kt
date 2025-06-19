package com.example.moneytalks.presentation.navigation
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.R
import com.example.moneytalks.data.BaseRepositoryImpl
import com.example.moneytalks.data.remote.RetrofitInstance
import com.example.moneytalks.presentation.account.AccountViewModel
import com.example.moneytalks.presentation.account.AccountsViewModelFactory
import com.example.moneytalks.presentation.common.TopAppBarState
import com.example.moneytalks.presentation.common.TopBar
import com.example.moneytalks.presentation.create_transaction.CreateTransactionIntent
import com.example.moneytalks.presentation.create_transaction.CreateTransactionViewModel
import com.example.moneytalks.presentation.create_transaction.CreateTransactionViewModelFactory


@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val repository = remember { BaseRepositoryImpl(RetrofitInstance.api) }

    //вьюмодели для доходов расходов
    val spendingViewModel: CreateTransactionViewModel = viewModel(
        key = "spendings",
        factory = CreateTransactionViewModelFactory(repository, type = "расходы")
    )
    val earningViewModel: CreateTransactionViewModel = viewModel(
        key = "earnings",
        factory = CreateTransactionViewModelFactory(repository, type = "доходы")
    )

    //вьюмодель для счетов
    val accountViewModel: AccountViewModel = viewModel(factory = AccountsViewModelFactory(repository))


    val accounts by accountViewModel.accounts.collectAsState()
    val selectedAccountId by accountViewModel.selectedAccountId.collectAsState()

    var showAccountMenu by remember { mutableStateOf(false) }

    val tabRoutes = listOf(
        "расходы_граф", "доходы_граф", "счет_граф", "статьи_граф", "настройки_граф"
    )

    val selectedTab = tabRoutes.indexOfFirst {
        currentRoute?.startsWith(it.removeSuffix("_граф")) == true
    }.let { if (it == -1) 0 else it }


    val topAppBarState = remember(currentBackStackEntry) {
        when (currentBackStackEntry?.destination?.route) {
            // Граф "Расходы"
            "расходы" -> TopAppBarState(
                title = "Расходы сегодня",
                trailingIcon = R.drawable.clocks,
                onTrailingIconClick = {
                    navController.navigate("расходы_история")
                }
            )
            "расходы_история" -> TopAppBarState(
                title = "Моя история",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.history,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = { navController.navigate("расходы_анализ") }
            )
            "расходы_добавить" -> TopAppBarState(
                title = "Мои расходы",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.ok,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = {
                    spendingViewModel.handleIntent(CreateTransactionIntent.SubmitTransaction)
                }
            )
            "расходы_анализ" -> TopAppBarState(
                title = "Анализ расходов",
                leadingIcon = R.drawable.back,
                onLeadingIconClick = { navController.popBackStack() }
            )

            // Граф "Доходы"
            "доходы" -> TopAppBarState(
                title = "Доходы сегодня",
                trailingIcon = R.drawable.clocks,
                onTrailingIconClick = {
                    navController.navigate("доходы_история")
                }
            )
            "доходы_история" -> TopAppBarState(
                title = "История доходов",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.history,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = { navController.navigate("доходы_анализ") }
            )
            "доходы_добавить" -> TopAppBarState(
                title = "Мои доходы",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.ok,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = {
                    earningViewModel.handleIntent(CreateTransactionIntent.SubmitTransaction)
                }
            )
            "доходы_анализ" -> TopAppBarState(
                title = "Анализ доходов",
                leadingIcon = R.drawable.back,
                onLeadingIconClick = { navController.popBackStack() }
            )

            // Граф "Счет"
            "счет" -> TopAppBarState(
                title = "Мои счета",
                leadingIcon = R.drawable.choose_account,
                onLeadingIconClick = {
                    showAccountMenu = true
                },
                trailingIcon = R.drawable.pen,
                onTrailingIconClick = { navController.navigate("счет_редактировать") }
            )
            "счет_добавить" -> TopAppBarState(
                title = "Мой счет",
                leadingIcon = R.drawable.cancel,
                onLeadingIconClick = { navController.popBackStack() },
                trailingIcon = R.drawable.ok,
                onTrailingIconClick = {

                }
            )
            "счет_редактировать" -> TopAppBarState(
                title = "Мой счет",
                leadingIcon = R.drawable.cancel,
                onLeadingIconClick = { navController.popBackStack() },
                trailingIcon = R.drawable.ok,
                onTrailingIconClick = {

                }
            )

            // Граф "Статьи"
            "статьи" -> TopAppBarState(
                title = "Статьи расходов",
            )

            // Граф "Настройки"
            "настройки" -> TopAppBarState(
                title = "Настройки"
            )

            else -> TopAppBarState()
        }
    }

    Scaffold(
        topBar = {
            Box {
                TopBar(
                    state = topAppBarState
                )
                DropdownMenu(
                    expanded = showAccountMenu,
                    onDismissRequest = { showAccountMenu = false }
                ) {
                    accounts.forEach { account ->
                        DropdownMenuItem(
                            text = { Text(account.name) },
                            onClick = {
                                accountViewModel.selectAccount(account.id)
                                showAccountMenu = false
                            }
                        )
                    }
                }
            }
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
                currentRoute == "доходы" ||
                currentRoute == "счет"
            ) {
                FloatingActionButton(
                    onClick = {
                        when {
                            currentRoute.startsWith("расходы") == true -> {
                                spendingViewModel.reset()
                                navController.navigate("расходы_добавить")
                            }
                            currentRoute.startsWith("доходы") == true -> {
                                earningViewModel.reset()
                                navController.navigate("доходы_добавить")
                            }
                            currentRoute.startsWith("счет") == true -> {
                                navController.navigate("счет_добавить")
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
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(padding),
            selectedAccountId = selectedAccountId,
            earningViewModel = earningViewModel,
            spendingViewModel = spendingViewModel,
            accountViewModel = accountViewModel
        )
    }
}
