package com.example.moneytalks
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.coreui.TopAppBarState
import com.example.moneytalks.coreui.composable.TopBar
import com.example.moneytalks.features.transaction.presentation.createSpendingTransaction.CreateSpendingTransactionIntent
import com.example.moneytalks.features.transaction.presentation.createSpendingTransaction.CreateSpendingTransactionViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytalks.core.network.NetworkMonitorViewModel
import com.example.moneytalks.features.account.presentation.account.AccountViewModel
import com.example.moneytalks.features.transaction.presentation.createEarningTransaction.CreateEarningTransactionIntent
import com.example.moneytalks.features.transaction.presentation.createEarningTransaction.CreateEarningTransactionViewModel
import com.example.moneytalks.features.transaction.presentation.transactions.TransactionViewModel
import com.example.moneytalks.navigation.MainNavHost
import com.example.moneytalks.navigation.Routes
import com.example.moneytalks.navigation.items

/**
 * Основной экран приложения.
 *
 * Включает верхний топ-бар, нижнюю навигацию, fab-кнопку и основной NavHost.
 * Управляет глобальными состояниями:
 *  - текущий выбранный счет
 *  - статус сети
 *  - выбранная вкладка навигации
 *
 * Особенности:
 * - Реагирует на изменение состояния сети, выводит предупреждение о потере соединения.
 * - Позволяет выбрать активный счет из выпадающего меню в топ-баре.
 * - FAB-кнопка динамически меняется в зависимости от текущей вкладки.
 * - Передаёт необходимые параметры (selectedAccountId, accountViewModel) во внутренний NavHost.
 *
 *
 * @see TopBar — верхняя панель с динамическими кнопками и меню выбора счета.
 * @see NavigationBar — нижняя навигация по основным разделам.
 * @see FloatingActionButton — кнопка для добавления транзакций или счета.
 * @see MainNavHost — основной граф навигации приложения.
 */

@Composable
fun MainAppScreen() {
    val networkMonitorViewModel: NetworkMonitorViewModel = hiltViewModel()
    val isConnected by networkMonitorViewModel.networkMonitor.isConnected.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val transactionEarningViewModel: TransactionViewModel = hiltViewModel(
        key = "earn"
    )

    val transactionSpendingViewModel: TransactionViewModel = hiltViewModel(
        key = "spend"
    )
    val spendingViewModel: CreateSpendingTransactionViewModel = hiltViewModel()
    val earningViewModel: CreateEarningTransactionViewModel = hiltViewModel()
    val accountViewModel: AccountViewModel = hiltViewModel()

    val accounts by accountViewModel.accounts.collectAsStateWithLifecycle()
    val selectedAccountId by accountViewModel.selectedAccountId.collectAsStateWithLifecycle()

    var showAccountMenu by remember { mutableStateOf(false) }

    val tabRoutes = listOf(
        Routes.EXPENSES_GRAPH, Routes.EARNINGS_GRAPH, Routes.ACCOUNTS_GRAPH, Routes.CATEGORIES_GRAPH, Routes.SETTINGS_GRAPH
    )

    val selectedTab = tabRoutes.indexOfFirst {
        currentRoute?.startsWith(it.removeSuffix("_граф")) == true
    }.let { if (it == -1) 0 else it }

    val topAppBarState = remember(currentBackStackEntry) {
        when (currentBackStackEntry?.destination?.route) {

            // Граф "Расходы"
            Routes.EXPENSES -> TopAppBarState(
                title = "Расходы сегодня",
                trailingIcon = R.drawable.clocks,
                onTrailingIconClick = {
                    navController.navigate(Routes.EXPENSES_HISTORY)
                }
            )
            Routes.EXPENSES_HISTORY -> TopAppBarState(
                title = "Моя история",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.history,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = { navController.navigate(Routes.EXPENSES_ANALYSIS) }
            )
            Routes.EXPENSES_ADD -> TopAppBarState(
                title = "Мои расходы",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.ok,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = {
                    spendingViewModel.handleIntent(CreateSpendingTransactionIntent.SubmitTransaction)
                }
            )
            Routes.EXPENSES_ANALYSIS -> TopAppBarState(
                title = "Анализ расходов",
                leadingIcon = R.drawable.back,
                onLeadingIconClick = { navController.popBackStack() }
            )

            // Граф "Доходы"
            Routes.EARNINGS -> TopAppBarState(
                title = "Доходы сегодня",
                trailingIcon = R.drawable.clocks,
                onTrailingIconClick = {
                    navController.navigate(Routes.EARNINGS_HISTORY)
                }
            )
            Routes.EARNINGS_HISTORY -> TopAppBarState(
                title = "История доходов",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.history,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = { navController.navigate(Routes.EARNINGS_ANALYSIS) }
            )
            Routes.EARNINGS_ADD -> TopAppBarState(
                title = "Мои доходы",
                leadingIcon = R.drawable.back,
                trailingIcon = R.drawable.ok,
                onLeadingIconClick = { navController.popBackStack() },
                onTrailingIconClick = {
                    earningViewModel.handleIntent(CreateEarningTransactionIntent.SubmitTransaction)
                }
            )
            Routes.EARNINGS_ANALYSIS -> TopAppBarState(
                title = "Анализ доходов",
                leadingIcon = R.drawable.back,
                onLeadingIconClick = { navController.popBackStack() }
            )

            // Граф "Счет"
            Routes.ACCOUNT -> TopAppBarState(
                title = "Мои счета",
                leadingIcon = R.drawable.choose_account,
                onLeadingIconClick = {
                    showAccountMenu = true
                },
                trailingIcon = R.drawable.pen,
                onTrailingIconClick = { navController.navigate(Routes.ACCOUNT_EDIT) }
            )
            Routes.ACCOUNT_ADD -> TopAppBarState(
                title = "Мой счет",
                leadingIcon = R.drawable.cancel,
                onLeadingIconClick = { navController.popBackStack() },
                trailingIcon = R.drawable.ok,
                onTrailingIconClick = {
                    // TODO: add submit logic
                }
            )
            Routes.ACCOUNT_EDIT -> TopAppBarState(
                title = "Мой счет",
                leadingIcon = R.drawable.cancel,
                onLeadingIconClick = { navController.popBackStack() },
                trailingIcon = R.drawable.ok,
                onTrailingIconClick = {
                    // TODO: add submit logic
                }
            )

            // Граф "Статьи"
            Routes.CATEGORIES -> TopAppBarState(
                title = "Статьи расходов",
            )

            // Граф "Настройки"
            Routes.SETTINGS -> TopAppBarState(
                title = "Настройки"
            )

            else -> TopAppBarState()
        }
    }

    if (!isConnected) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.Red),
            Alignment.Center
        ) {
            Text(
                "Нет соединения с интернетом",
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
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
                        Log.d("accs", "${account.name}")
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
            if (
                currentRoute == Routes.EXPENSES ||
                currentRoute == Routes.EARNINGS ||
                currentRoute == Routes.ACCOUNT
            ) {
                FloatingActionButton(
                    onClick = {
                        when {
                            currentRoute == Routes.EXPENSES -> {
                                spendingViewModel.reset()
                                navController.navigate(Routes.EXPENSES_ADD)
                            }
                            currentRoute == Routes.EARNINGS -> {
                                earningViewModel.reset()
                                navController.navigate(Routes.EARNINGS_ADD)
                            }
                            currentRoute == Routes.ACCOUNT -> {
                                navController.navigate(Routes.ACCOUNT_ADD)
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
            accountViewModel = accountViewModel,
            transactionSpendingViewModel = transactionSpendingViewModel,
            transactionEarningViewModel = transactionEarningViewModel,
            spendingViewModel = spendingViewModel,
            earningViewModel = earningViewModel,
        )
    }
}

