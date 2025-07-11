package com.example.feature_expenses.ui.incomes.incomes_add

import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import java.time.LocalDateTime

data class IncomesAddState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),

    val selectedAccount: Account? = null,
    val selectedCategory: Category? = null,
    val amount: String = "",
    val selectedDateTime: LocalDateTime = LocalDateTime.now(),
    val comment: String = "",

    val isFormValid: Boolean = false,
    val amountError: String? = null,

    val isAccountDropdownExpanded: Boolean = false,
    val isCategoryDropdownExpanded: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val isCreating: Boolean = false,
    val isCreated: Boolean = false
)