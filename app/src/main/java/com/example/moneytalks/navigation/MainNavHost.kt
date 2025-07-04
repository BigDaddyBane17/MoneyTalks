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
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.EXPENSES_GRAPH,
    selectedAccountId: Int?,
    accountViewModel: AccountViewModel
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
                    accountId = selectedAccountId,
                    type = Routes.EXPENSES,
                    navigateToHistory = {
                        navController.navigate(Routes.EXPENSES_HISTORY)
                    },
                    navigateToAddTransaction = {
                        navController.navigate(Routes.EXPENSES_ADD)
                    },
                    accountViewModel = accountViewModel

                )
            }
            composable(Routes.EXPENSES_HISTORY) {
                HistoryScreen(
                    type = Routes.EXPENSES,
                    accountId = selectedAccountId,
                    navigateToAnalysis = {
                        navController.navigate(Routes.EXPENSES_ANALYSIS)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    },
                    accountViewModel = accountViewModel
                )
            }
            composable(Routes.EXPENSES_ADD) {
                CreateSpendingTransactionScreen(
                    navController = navController,
                )
            }
            composable(Routes.EXPENSES_ANALYSIS) {
                AnalysisScreen(
                    type = Routes.EXPENSES
                )
            }
        }

        // Граф "Доходы"
        navigation(startDestination = Routes.EARNINGS, route = Routes.EARNINGS_GRAPH) {
            composable(Routes.EARNINGS) {
                TransactionScreen(
                    accountId = selectedAccountId,
                    type = Routes.EARNINGS,
                    navigateToHistory = {
                        navController.navigate(Routes.EARNINGS_HISTORY)
                    },
                    navigateToAddTransaction = {
                        navController.navigate(Routes.EARNINGS_ADD)
                    },
                    accountViewModel = accountViewModel

                )
            }
            composable(Routes.EARNINGS_HISTORY) {
                HistoryScreen(
                    type = Routes.EARNINGS,
                    accountId = selectedAccountId,
                    navigateToAnalysis = {
                        navController.navigate(Routes.EARNINGS_ANALYSIS)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    },
                    accountViewModel = accountViewModel
                )
            }
            composable(Routes.EARNINGS_ADD) {
                CreateEarningTransactionScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable(Routes.EARNINGS_ANALYSIS) {
                AnalysisScreen(
                    type = Routes.EARNINGS
                )
            }
        }

        // Граф "Счет"
        navigation(startDestination = Routes.ACCOUNT, route = Routes.ACCOUNTS_GRAPH) {
            composable(Routes.ACCOUNT) {
                AccountScreen(
                    viewModel = accountViewModel,
                    navigateToAccountEdit = {
                        navController.navigate(Routes.ACCOUNT_EDIT)
                    }
                )
            }
            composable(Routes.ACCOUNT_ADD) {
                CreateAccount()
            }
            composable(Routes.ACCOUNT_EDIT) {
                EditAccountScreen(
                    onBack = { navController.popBackStack() },
                    accountViewModel = accountViewModel
                )
            }
        }

        // Граф "Статьи"
        navigation(startDestination = Routes.CATEGORIES, route = Routes.CATEGORIES_GRAPH) {
            composable(Routes.CATEGORIES) {
                CategoryScreen()
            }
        }

        // Граф "Настройки"
        navigation(startDestination = Routes.SETTINGS, route = Routes.SETTINGS_GRAPH) {
            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
