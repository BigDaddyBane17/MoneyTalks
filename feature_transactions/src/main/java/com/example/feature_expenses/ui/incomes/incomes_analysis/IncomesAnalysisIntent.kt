package com.example.feature_expenses.ui.incomes.incomes_analysis

import java.time.LocalDate

sealed interface IncomesAnalysisIntent {
    data class LoadHistory(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : IncomesAnalysisIntent
    
    data object Refresh : IncomesAnalysisIntent
}