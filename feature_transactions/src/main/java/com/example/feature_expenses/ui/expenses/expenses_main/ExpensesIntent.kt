package com.example.feature_expenses.ui.expenses.expenses_main

sealed interface ExpensesIntent {
    data object LoadTodayExpenses : ExpensesIntent
    data object Refresh : ExpensesIntent
}