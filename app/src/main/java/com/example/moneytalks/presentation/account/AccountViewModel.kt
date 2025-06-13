package com.example.moneytalks.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.model.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    val account = Account(1, "-670 000 ₽", "₽")

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            AccountIntent.LoadAccountData -> loadAccount()
            AccountIntent.BalanceClick -> goToBalance()
            AccountIntent.CurrencyClick -> changeCurrency()
            AccountIntent.CreateAccount -> createAccount()
        }
    }

    private fun loadAccount() {
        _uiState.value = AccountUiState.Loading
        viewModelScope.launch {
            delay(300)
            _uiState.value = AccountUiState.Success(
                account
            )
        }
    }

    private fun goToBalance() {

    }

    private fun changeCurrency() {

    }

    private fun createAccount() {

    }

}