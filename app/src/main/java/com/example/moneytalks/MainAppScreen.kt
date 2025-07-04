package com.example.moneytalks
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.core.network.NetworkMonitorViewModel
import com.example.moneytalks.coreui.composable.BottomBar
import com.example.moneytalks.features.account.presentation.account.AccountViewModel
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
 * @see BottomBar — нижняя навигация по основным разделам.
 * @see MainNavHost — основной граф навигации приложения.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainAppScreen() {
    val networkMonitorViewModel: NetworkMonitorViewModel = hiltViewModel()
    val isConnected by networkMonitorViewModel.networkMonitor.isConnected.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val accountViewModel: AccountViewModel = hiltViewModel()
    val selectedAccountId by accountViewModel.selectedAccountId.collectAsStateWithLifecycle()

    val tabRoutes = listOf(
        Routes.EXPENSES_GRAPH, Routes.EARNINGS_GRAPH, Routes.ACCOUNTS_GRAPH, Routes.CATEGORIES_GRAPH, Routes.SETTINGS_GRAPH
    )

    val selectedTab = tabRoutes.indexOfFirst {
        currentRoute?.startsWith(it.removeSuffix("_граф")) == true
    }.let { if (it == -1) 0 else it }


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
        bottomBar = {
            BottomBar(
                tabRoutes = tabRoutes,
                navController = navController,
                selectedTab = selectedTab,
            )
        },
        containerColor = Color(0xFFFef7ff),
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            selectedAccountId = selectedAccountId,
            accountViewModel = accountViewModel,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()) // если передать фулл, то он сверху делает отступ непонятный

        )
    }
}

