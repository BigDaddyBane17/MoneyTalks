package com.example.feature_expenses.ui.expenses.expenses_edit

import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import java.time.LocalDate
import java.time.LocalTime

sealed class ExpensesEditIntent {
    object LoadTransaction : ExpensesEditIntent()
    data class SelectAccount(val account: Account) : ExpensesEditIntent()
    data class SelectCategory(val category: Category) : ExpensesEditIntent()
    data class AmountChanged(val amount: String) : ExpensesEditIntent()
    data class DateSelected(val date: LocalDate) : ExpensesEditIntent()
    data class TimeSelected(val time: LocalTime) : ExpensesEditIntent()
    data class CommentChanged(val comment: String) : ExpensesEditIntent()
    object UpdateTransaction : ExpensesEditIntent()
    object DeleteTransaction : ExpensesEditIntent()
    object ClearError : ExpensesEditIntent()
} 