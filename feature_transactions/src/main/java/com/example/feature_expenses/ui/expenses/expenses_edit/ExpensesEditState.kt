package com.example.feature_expenses.ui.expenses.expenses_edit

import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import java.time.LocalDateTime

data class ExpensesEditState(
    val transactionId: Int = 0,
    val isLoading: Boolean = true,
    val isUpdating: Boolean = false,
    val isDeleting: Boolean = false,
    val isUpdated: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null,
    
    // Form fields
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedAccount: Account? = null,
    val selectedCategory: Category? = null,
    val amount: String = "",
    val selectedDateTime: LocalDateTime = LocalDateTime.now(),
    val comment: String = "",
    
    // Validation
    val amountError: String? = null,
    val isFormValid: Boolean = false
) 