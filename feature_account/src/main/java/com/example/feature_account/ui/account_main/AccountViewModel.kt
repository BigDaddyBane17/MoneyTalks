package com.example.feature_account.ui.account_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.usecase.GetCurrentAccountUseCase
import com.example.feature_account.domain.usecase.EditAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val selectedAccountRepository: SelectedAccountRepository,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
    private val editAccountUseCase: EditAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private var selectedAccountId: Int? = null

    init {
        handleIntent(AccountIntent.LoadAccounts)
    }

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.LoadAccounts -> loadAccounts()
            is AccountIntent.SelectAccount -> selectAccount(intent.accountId)
            is AccountIntent.CurrencyClick -> updateCurrency(intent.currencyCode)
            is AccountIntent.Refresh -> loadAccounts()
        }
    }

    fun selectAccount(accountId: Int) {
        selectedAccountId = accountId
        viewModelScope.launch {
            selectedAccountRepository.setSelectedAccountId(accountId)
            loadCurrentAccount()
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _uiState.value = AccountUiState.Loading
            try {
                val accounts = accountRepository.getAccounts()
                if (accounts.isNotEmpty()) {
                    if (selectedAccountId == null) {
                        val currentAccount = getCurrentAccountUseCase.getCurrentAccount()
                        selectedAccountId = currentAccount?.id ?: accounts.first().id
                    }
                    val currentAccount = accounts.find { it.id == selectedAccountId }
                    _uiState.value = AccountUiState.Success(
                        account = currentAccount,
                        accounts = accounts
                    )
                } else {
                    _uiState.value = AccountUiState.Error("Нет доступных счетов. Проверьте подключение к интернету.")
                }
            } catch (e: Exception) {
                _uiState.value = AccountUiState.Error(
                    "Ошибка загрузки счетов: ${e.message ?: "Проверьте подключение к интернету"}"
                )
            }
        }
    }

    private fun loadCurrentAccount() {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is AccountUiState.Success && selectedAccountId != null) {
                    val updatedAccount = currentState.accounts.find { it.id == selectedAccountId }
                    _uiState.value = currentState.copy(account = updatedAccount)
                }
            } catch (e: Exception) {
                _uiState.value = AccountUiState.Error(
                    "Ошибка загрузки счета: ${e.message ?: "Проверьте подключение к интернету"}"
                )
            }
        }
    }

    private fun updateCurrency(currencyCode: String) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is AccountUiState.Success && currentState.account != null) {
                    val account = currentState.account
                    val updatedAccount = editAccountUseCase(
                        accountId = account.id,
                        name = account.name,
                        currency = currencyCode
                    )
                    
                    // Обновляем состояние с новой валютой
                    val updatedAccounts = currentState.accounts.map { acc ->
                        if (acc.id == updatedAccount.id) updatedAccount else acc
                    }
                    
                    _uiState.value = currentState.copy(
                        account = updatedAccount,
                        accounts = updatedAccounts
                    )
                }
            } catch (e: Exception) {
                _uiState.value = AccountUiState.Error(
                    "Ошибка обновления валюты: ${e.message ?: "Проверьте подключение к интернету"}"
                )
            }
        }
    }
}