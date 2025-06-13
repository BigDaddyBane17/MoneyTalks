package com.example.moneytalks.presentation.earnings


import com.example.moneytalks.domain.model.Income

sealed class EarningsUiState {
    object Loading: EarningsUiState()
    data class Success(val items: List<Income>) : EarningsUiState()
    data class Error(val message: String): EarningsUiState()
}