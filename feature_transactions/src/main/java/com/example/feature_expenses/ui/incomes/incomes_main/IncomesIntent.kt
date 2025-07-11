package com.example.feature_expenses.ui.incomes.incomes_main

sealed interface IncomesIntent {
    data object LoadTodayIncomes : IncomesIntent
    data object Refresh : IncomesIntent
}