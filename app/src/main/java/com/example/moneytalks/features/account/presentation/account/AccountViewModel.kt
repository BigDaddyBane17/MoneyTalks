package com.example.moneytalks.features.account.presentation.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.core.network.retryIO
import com.example.moneytalks.features.account.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel для получения, выбора и отображения счеирв, а также обработки состояния загрузки/ошибок.
 */


//todo убрать логику репозитория отсюда

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: AccountRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val _accounts = MutableStateFlow<List<AccountDto>>(emptyList())
    val accounts: StateFlow<List<AccountDto>> = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<AccountDto?>(null)
    val selectedAccount: StateFlow<AccountDto?> = _selectedAccount.asStateFlow()


    private val _selectedAccountId = MutableStateFlow<Int?>(null)
    val selectedAccountId: StateFlow<Int?> = _selectedAccountId.asStateFlow()


    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            AccountIntent.LoadAccountData -> loadAccounts()
            is AccountIntent.CurrencyClick -> changeCurrency(intent.currency)
            AccountIntent.BalanceClick -> TODO()
        }
    }

    fun loadAccounts() {
        viewModelScope.launch {
            if (!networkMonitor.isConnected.value) {
                _uiState.value = AccountUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            try {
                val loadedAccounts = retryIO(times = 3, delayMillis = 2000) {
                    repository.getAccounts()
                }
                _accounts.value = loadedAccounts

                if (_selectedAccountId.value == null || loadedAccounts.none { it.id == _selectedAccountId.value }) {
                    _selectedAccountId.value = loadedAccounts.firstOrNull()?.id
                }

                val selected = loadedAccounts.firstOrNull { it.id == _selectedAccountId.value }
                    ?: loadedAccounts.firstOrNull()

                _selectedAccount.value = selected
                _uiState.value = AccountUiState.Success(selected)
            } catch (e: IOException) {
                _uiState.value = AccountUiState.Error("Нет соединения с интернетом")
            } catch (e: Exception) {
                _uiState.value = AccountUiState.Error("Ошибка загрузки: ${e.localizedMessage}")
            }
        }
    }


    fun selectAccount(accountId: Int) {
        _selectedAccountId.value = accountId
        val selected = _accounts.value.firstOrNull { it.id == accountId }
        _selectedAccount.value = selected
        _uiState.value = AccountUiState.Success(selected)
    }

    fun changeCurrency(newCurrency: String) {
        val current = _selectedAccount.value ?: return
        viewModelScope.launch {
            try {
                val request = AccountUpdateRequestDto(
                    name = current.name,
                    balance = current.balance,
                    currency = newCurrency
                )
                val updatedAccount = repository.updateAccount(current.id, request)
                _selectedAccount.value = updatedAccount
                _uiState.value = AccountUiState.Success(updatedAccount)
            } catch (e: Exception) {
                _uiState.value = AccountUiState.Error("Ошибка обновления валюты: ${e.localizedMessage}")
            }
        }
    }

}