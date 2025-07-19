package com.example.feature_expenses.ui.expenses.expenses_analysis

import com.example.domain.models.Transaction

data class ExpensesAnalysisState(
    val isLoading: Boolean = false,
    val expenses: List<Transaction> = emptyList(),
    val error: String? = null,
    val totalAmount: String = "0.00",
    val currency: String = "",
    val accountId: Int? = null
)