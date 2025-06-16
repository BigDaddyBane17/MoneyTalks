package com.example.moneytalks.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.model.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<AccountNavigationEvent>()
    val navigationEvent: SharedFlow<AccountNavigationEvent> = _navigationEvent.asSharedFlow()


    val account = Account(1, "-670 000 ₽", "₽")

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            AccountIntent.LoadAccountData -> loadAccount()
            AccountIntent.BalanceClick -> goToBalance()
            is AccountIntent.CurrencyClick -> changeCurrency(intent.currency)
            //AccountIntent.CreateAccount -> createAccount()
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

    private fun changeCurrency(currency: String) {
        _uiState.update { oldState ->
            if(oldState is AccountUiState.Success) {
                oldState.copy(account = oldState.account.copy(currency = currency))
            }
            else {
                oldState
            }
        }
    }

    private fun goToBalance() {
        viewModelScope.launch {
            _navigationEvent.emit(AccountNavigationEvent.NavigateToBalance)
        }
    }

//    private fun createAccount() {
//
//    }

}