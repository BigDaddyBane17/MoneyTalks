package com.example.feature_expenses.ui.incomes.incomes_add

import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import java.time.LocalDate
import java.time.LocalTime

sealed interface IncomesAddIntent {
    data object LoadInitialData : IncomesAddIntent
    
    // Account selection
    data object ToggleAccountDropdown : IncomesAddIntent
    data class SelectAccount(val account: Account) : IncomesAddIntent
    
    // Category selection
    data object ToggleCategoryDropdown : IncomesAddIntent
    data class SelectCategory(val category: Category) : IncomesAddIntent
    
    // Amount input
    data class AmountChanged(val amount: String) : IncomesAddIntent
    
    // Date/Time selection
    data object ShowDatePicker : IncomesAddIntent
    data object HideDatePicker : IncomesAddIntent
    data class DateSelected(val date: LocalDate) : IncomesAddIntent
    
    data object ShowTimePicker : IncomesAddIntent
    data object HideTimePicker : IncomesAddIntent
    data class TimeSelected(val time: LocalTime) : IncomesAddIntent
    
    // Comment input
    data class CommentChanged(val comment: String) : IncomesAddIntent
    
    // Transaction creation
    data object CreateTransaction : IncomesAddIntent
    
    // Error handling
    data object ClearError : IncomesAddIntent
}