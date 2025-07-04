package com.example.moneytalks.features.account.presentation.edit_account


sealed class EditAccountUiState {
    object Loading : EditAccountUiState()
    object Success: EditAccountUiState()
    data class Error(val message: String) : EditAccountUiState()
}
