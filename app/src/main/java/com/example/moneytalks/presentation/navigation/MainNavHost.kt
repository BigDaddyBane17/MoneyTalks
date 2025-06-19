package com.example.moneytalks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moneytalks.presentation.account.AccountScreen
import com.example.moneytalks.presentation.account.AccountViewModel
import com.example.moneytalks.presentation.analysis.AnalysisScreen
import com.example.moneytalks.presentation.create_account.CreateAccount

import com.example.moneytalks.presentation.create_transaction.CreateTransactionScreen
import com.example.moneytalks.presentation.create_transaction.CreateTransactionViewModel
import com.example.moneytalks.presentation.edit_account.EditAccountScreen
import com.example.moneytalks.presentation.item_expenses.ItemExpensesScreen
import com.example.moneytalks.presentation.settings.SettingsScreen
import com.example.moneytalks.presentation.transactions.SpendingScreen
import com.example.moneytalks.presentation.history.HistoryScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "расходы_граф",
    selectedAccountId: Int?,
    spendingViewModel: CreateTransactionViewModel,
    earningViewModel: CreateTransactionViewModel,
    accountViewModel: AccountViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Граф "Расходы"
        navigation(startDestination = "расходы", route = "расходы_граф") {
            composable("расходы") { SpendingScreen(
                navController = navController,
                accountId = selectedAccountId,
                type = "расходы"
            ) }
            composable("расходы_история") {
                HistoryScreen(
                    navController = navController,
                    type = "расходы",
                    accountId = selectedAccountId
                )
            }
            composable("расходы_добавить") {
                CreateTransactionScreen(
                    navController = navController,
                    type = "расходы",
                    viewModel = spendingViewModel
                )
            }
            composable("расходы_анализ") { AnalysisScreen(navController = navController, type = "расходы") }
        }
        // Граф "Доходы"
        navigation(startDestination = "доходы", route = "доходы_граф") {
            composable("доходы") { SpendingScreen(
                navController = navController,
                accountId = selectedAccountId,
                type = "доходы"
            ) }
            composable("доходы_история") {
                HistoryScreen(
                    navController = navController,
                    type = "доходы",
                    accountId = selectedAccountId
                )
            }
            composable("доходы_добавить") {
                CreateTransactionScreen(
                    navController = navController,
                    type = "доходы",
                    viewModel = earningViewModel
                )
            }
            composable("доходы_анализ") { AnalysisScreen(navController = navController, type = "доходы") }
        }
        // Граф "Счет"
        navigation(startDestination = "счет", route = "счет_граф") {
            composable("счет") {
                AccountScreen(
                    navController = navController,
                    viewModel = accountViewModel
                )
            }
            composable("счет_добавить") { CreateAccount(navController = navController) }
            composable("счет_редактировать") { EditAccountScreen(navController = navController) }
        }
        // Граф "Статьи"
        navigation(startDestination = "статьи", route = "статьи_граф") {
            composable("статьи") { ItemExpensesScreen(navController = navController) }
        }
        // Граф "Настройки"
        navigation(startDestination = "настройки", route = "настройки_граф") {
            composable("настройки") { SettingsScreen(navController = navController) }
        }
    }
}


