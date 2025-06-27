package com.example.moneytalks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moneytalks.features.account.presentation.account.AccountScreen
import com.example.moneytalks.features.account.presentation.account.AccountViewModel
import com.example.moneytalks.features.transaction.presentation.analysis.AnalysisScreen
import com.example.moneytalks.features.account.presentation.create_account.CreateAccount
import com.example.moneytalks.features.transaction.presentation.createEarningTransaction.CreateEarningTransactionViewModel
import com.example.moneytalks.features.transaction.presentation.createSpendingTransaction.CreateSpendingTransactionViewModel
import com.example.moneytalks.features.account.presentation.edit_account.EditAccountScreen
import com.example.moneytalks.features.categories.presentation.category.CategoryScreen
import com.example.moneytalks.features.settings.presentation.settings.SettingsScreen
import com.example.moneytalks.features.transaction.presentation.createEarningTransaction.CreateEarningTransactionScreen
import com.example.moneytalks.features.transaction.presentation.createSpendingTransaction.CreateSpendingTransactionScreen
import com.example.moneytalks.features.transaction.presentation.history.HistoryScreen
import com.example.moneytalks.features.transaction.presentation.transactions.TransactionScreen
import com.example.moneytalks.features.transaction.presentation.transactions.TransactionViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.EXPENSES_GRAPH,
    selectedAccountId: Int?,
    accountViewModel: AccountViewModel,
    transactionSpendingViewModel: TransactionViewModel,
    transactionEarningViewModel: TransactionViewModel,
    spendingViewModel: CreateSpendingTransactionViewModel,
    earningViewModel: CreateEarningTransactionViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Граф "Расходы"
        navigation(startDestination = Routes.EXPENSES, route = Routes.EXPENSES_GRAPH) {
            composable(Routes.EXPENSES) {
                TransactionScreen(
                    navController = navController,
                    accountId = selectedAccountId,
                    viewModel = transactionSpendingViewModel,
                    type = Routes.EXPENSES
                )
            }
            composable(Routes.EXPENSES_HISTORY) {
                HistoryScreen(
                    navController = navController,
                    type = Routes.EXPENSES,
                    accountId = selectedAccountId
                )
            }
            composable(Routes.EXPENSES_ADD) {
                CreateSpendingTransactionScreen(
                    navController = navController,
                    viewModel = spendingViewModel
                )
            }
            composable(Routes.EXPENSES_ANALYSIS) {
                AnalysisScreen(
                    navController = navController,
                    type = Routes.EXPENSES
                )
            }
        }

        // Граф "Доходы"
        navigation(startDestination = Routes.EARNINGS, route = Routes.EARNINGS_GRAPH) {
            composable(Routes.EARNINGS) {
                TransactionScreen(
                    navController = navController,
                    accountId = selectedAccountId,
                    viewModel = transactionEarningViewModel,
                    type = Routes.EARNINGS
                )
            }
            composable(Routes.EARNINGS_HISTORY) {
                HistoryScreen(
                    navController = navController,
                    type = Routes.EARNINGS,
                    accountId = selectedAccountId
                )
            }
            composable(Routes.EARNINGS_ADD) {
                CreateEarningTransactionScreen(
                    navController = navController,
                    viewModel = earningViewModel
                )
            }
            composable(Routes.EARNINGS_ANALYSIS) {
                AnalysisScreen(
                    navController = navController,
                    type = Routes.EARNINGS
                )
            }
        }

        // Граф "Счет"
        navigation(startDestination = Routes.ACCOUNT, route = Routes.ACCOUNTS_GRAPH) {
            composable(Routes.ACCOUNT) {
                AccountScreen(
                    navController = navController,
                    viewModel = accountViewModel
                )
            }
            composable(Routes.ACCOUNT_ADD) {
                CreateAccount(navController = navController)
            }
            composable(Routes.ACCOUNT_EDIT) {
                EditAccountScreen(navController = navController)
            }
        }

        // Граф "Статьи"
        navigation(startDestination = Routes.CATEGORIES, route = Routes.CATEGORIES_GRAPH) {
            composable(Routes.CATEGORIES) {
                CategoryScreen(navController = navController)
            }
        }

        // Граф "Настройки"
        navigation(startDestination = Routes.SETTINGS, route = Routes.SETTINGS_GRAPH) {
            composable(Routes.SETTINGS) {
                SettingsScreen(navController = navController)
            }
        }
    }
}
