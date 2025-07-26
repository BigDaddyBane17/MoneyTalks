package com.example.feature_expenses.ui.incomes.incomes_analysis

import com.example.domain.models.Transaction

data class IncomesAnalysisState(
    val isLoading: Boolean = false,
    val expenses: List<Transaction> = emptyList(), // переименовать
    val error: String? = null,
    val totalAmount: String = "0.00",
    val currency: String = "",
    val accountId: Int? = null
)