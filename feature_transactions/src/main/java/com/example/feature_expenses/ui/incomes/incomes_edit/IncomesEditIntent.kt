package com.example.feature_expenses.ui.incomes.incomes_edit

import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import java.time.LocalDate
import java.time.LocalTime

sealed class IncomesEditIntent {
    object LoadTransaction : IncomesEditIntent()
    data class SelectAccount(val account: Account) : IncomesEditIntent()
    data class SelectCategory(val category: Category) : IncomesEditIntent()
    data class AmountChanged(val amount: String) : IncomesEditIntent()
    data class DateSelected(val date: LocalDate) : IncomesEditIntent()
    data class TimeSelected(val time: LocalTime) : IncomesEditIntent()
    data class CommentChanged(val comment: String) : IncomesEditIntent()
    object UpdateTransaction : IncomesEditIntent()
    object DeleteTransaction : IncomesEditIntent()
    object ClearError : IncomesEditIntent()
} 