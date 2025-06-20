package com.example.moneytalks.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.network.NetworkMonitor
import com.example.moneytalks.network.retryIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AccountViewModel(
    private val repository: BaseRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()


    private val _accounts = MutableStateFlow<List<AccountDto>>(emptyList())
    val accounts: StateFlow<List<AccountDto>> = _accounts.asStateFlow()

    private val _selectedAccountId = MutableStateFlow<Int?>(null)
    val selectedAccountId: StateFlow<Int?> = _selectedAccountId.asStateFlow()


    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            AccountIntent.LoadAccountData -> loadAccounts()
            is AccountIntent.CurrencyClick -> changeCurrency(intent.currency)
            AccountIntent.BalanceClick -> TODO()
        }
    }

    init {
        loadAccounts()
    }

    fun loadAccounts() {
        viewModelScope.launch {
            if (!networkMonitor.isConnected.value) {
                _uiState.value = AccountUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            try {
                val loadedAccounts = retryIO(times = 3, delayMillis = 2000){
                    repository.getAccounts()
                }
                _accounts.value = loadedAccounts

                if (_selectedAccountId.value == null || loadedAccounts.none { it.id == _selectedAccountId.value }) {
                    _selectedAccountId.value = loadedAccounts.firstOrNull()?.id
                }

                val selected = loadedAccounts.firstOrNull { it.id == _selectedAccountId.value }
                    ?: loadedAccounts.firstOrNull()

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
        _uiState.value = AccountUiState.Success(selected)
    }

    private fun changeCurrency(currency: String) {
//        _uiState.update { oldState ->
//            if(oldState is AccountUiState.Success) {
//                oldState.copy(account = oldState.account?.copy(currency = currency))
//            }
//            else {
//                oldState
//            }
//        }
    }


}