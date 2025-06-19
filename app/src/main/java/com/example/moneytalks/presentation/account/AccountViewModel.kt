package com.example.moneytalks.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.presentation.create_transaction.CreateTransactionUiState
import com.example.moneytalks.presentation.spendings.SpendingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<AccountNavigationEvent>()
    val navigationEvent: SharedFlow<AccountNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccountId = MutableStateFlow<Int?>(null)
    val selectedAccountId: StateFlow<Int?> = _selectedAccountId.asStateFlow()

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            AccountIntent.LoadAccountData -> loadAccounts()
            is AccountIntent.CurrencyClick -> changeCurrency(intent.currency)
            //AccountIntent.CreateAccount -> createAccount()
            AccountIntent.BalanceClick -> TODO()
        }
    }

    init { loadAccounts() }

    fun loadAccounts() {
        viewModelScope.launch {
            val loadedAccounts = repository.getAccounts()
            _accounts.value = loadedAccounts

            if (_selectedAccountId.value == null && loadedAccounts.isNotEmpty()) {
                _selectedAccountId.value = loadedAccounts.first().id
            }

            val selected = loadedAccounts.firstOrNull { it.id == _selectedAccountId.value }
                ?: loadedAccounts.firstOrNull()

            _uiState.value = AccountUiState.Success(selected)
        }
    }

    fun selectAccount(accountId: Int) {
        _selectedAccountId.value = accountId
        val selected = _accounts.value.firstOrNull { it.id == accountId }
        _uiState.value = AccountUiState.Success(selected)
    }

    private fun changeCurrency(currency: String) {
        _uiState.update { oldState ->
            if(oldState is AccountUiState.Success) {
                oldState.copy(account = oldState.account?.copy(currency = currency))
            }
            else {
                oldState
            }
        }
    }

    private fun createAccount() {

    }

    private fun goToBalance() {
        viewModelScope.launch {
            _navigationEvent.emit(AccountNavigationEvent.NavigateToBalance)
        }
    }


}