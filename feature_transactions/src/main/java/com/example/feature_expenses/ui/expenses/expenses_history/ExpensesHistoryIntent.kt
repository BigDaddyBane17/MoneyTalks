package com.example.feature_expenses.ui.expenses.expenses_history

import java.time.LocalDate

sealed interface ExpensesHistoryIntent {
    data class LoadHistory(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : ExpensesHistoryIntent
    
    data object Refresh : ExpensesHistoryIntent
}