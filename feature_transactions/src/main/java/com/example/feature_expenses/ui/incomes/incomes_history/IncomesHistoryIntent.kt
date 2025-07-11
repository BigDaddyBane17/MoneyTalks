package com.example.feature_expenses.ui.incomes.incomes_history

import java.time.LocalDate

sealed interface IncomesHistoryIntent {
    data class LoadHistory(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : IncomesHistoryIntent
    
    data object Refresh : IncomesHistoryIntent
}