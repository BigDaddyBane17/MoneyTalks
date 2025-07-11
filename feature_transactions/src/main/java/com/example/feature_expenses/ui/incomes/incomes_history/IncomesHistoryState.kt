package com.example.feature_expenses.ui.incomes.incomes_history

import com.example.domain.models.Transaction

data class IncomesHistoryState(
    val isLoading: Boolean = false,
    val incomes: List<Transaction> = emptyList(),
    val error: String? = null,
    val totalAmount: String = "0.00",
    val currency: String = "",
    val accountId: Int? = null
)