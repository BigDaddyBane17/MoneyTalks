package com.example.feature_account.ui.account_edit

import com.example.core.domain.models.Account

data class AccountEditState(
    val isLoading: Boolean = false,
    val account: Account? = null,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val nameError: String? = null,
    val balanceError: String? = null,
    val isFormValid: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)