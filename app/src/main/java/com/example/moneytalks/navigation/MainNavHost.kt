package com.example.moneytalks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.features.account.presentation.account.AccountScreen
import com.example.moneytalks.features.account.presentation.account.AccountViewModel
import com.example.moneytalks.features.transaction.presentation.analysis.AnalysisScreen
import com.example.moneytalks.features.account.presentation.create_account.CreateAccount
import com.example.moneytalks.features.transaction.presentation.create_earning_transaction.CreateEarningTransactionViewModel
import com.example.moneytalks.features.transaction.presentation.create_spending_transaction.CreateSpendingTransactionViewModel
import com.example.moneytalks.features.account.presentation.edit_account.EditAccountScreen
import com.example.moneytalks.features.categories.presentation.item_expenses.ItemExpensesScreen
import com.example.moneytalks.features.settings.presentation.settings.SettingsScreen
import com.example.moneytalks.features.transaction.presentation.create_earning_transaction.CreateEarningTransactionScreen
import com.example.moneytalks.features.transaction.presentation.create_spending_transaction.CreateSpendingTransactionScreen
import com.example.moneytalks.features.transaction.presentation.history.HistoryScreen
import com.example.moneytalks.features.transaction.presentation.transactions.TransactionScreen
import com.example.moneytalks.features.transaction.presentation.transactions.TransactionViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "расходы_граф",
    selectedAccountId: Int?,
    spendingViewModel: CreateSpendingTransactionViewModel,
    earningViewModel: CreateEarningTransactionViewModel,
    transactionSpendingViewModel: TransactionViewModel,
    transactionEarningViewModel: TransactionViewModel,
    accountViewModel: AccountViewModel,
    networkMonitor: NetworkMonitor
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Граф "Расходы"
        navigation(startDestination = "расходы", route = "расходы_граф") {
            composable("расходы") { TransactionScreen(
                navController = navController,
                accountId = selectedAccountId,
                viewModel = transactionSpendingViewModel,
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
                CreateSpendingTransactionScreen(
                    navController = navController,
                    viewModel = spendingViewModel
                )
            }
            composable("расходы_анализ") { AnalysisScreen(navController = navController, type = "расходы") }
        }


        // Граф "Доходы"
        navigation(startDestination = "доходы", route = "доходы_граф") {
            composable("доходы") { TransactionScreen(
                navController = navController,
                accountId = selectedAccountId,
                viewModel = transactionEarningViewModel,
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
                CreateEarningTransactionScreen(
                    navController = navController,
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


