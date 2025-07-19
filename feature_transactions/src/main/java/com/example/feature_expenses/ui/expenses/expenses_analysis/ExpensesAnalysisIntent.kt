package com.example.feature_expenses.ui.expenses.expenses_analysis

import java.time.LocalDate

sealed interface ExpensesAnalysisIntent {
    data class LoadHistory(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : ExpensesAnalysisIntent
    
    data object Refresh : ExpensesAnalysisIntent
}