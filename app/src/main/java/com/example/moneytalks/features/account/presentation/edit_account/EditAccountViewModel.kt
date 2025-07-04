package com.example.moneytalks.features.account.presentation.edit_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.account.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import com.example.moneytalks.features.account.presentation.account.AccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val repository: AccountRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _account = MutableStateFlow<AccountDto?>(null)
    val account: StateFlow<AccountDto?> = _account.asStateFlow()

    private val _uiState = MutableStateFlow<EditAccountUiState>(EditAccountUiState.Loading)
    val uiState: StateFlow<EditAccountUiState> = _uiState.asStateFlow()

    fun setAccount(account: AccountDto?) {
        _account.value = account
        _uiState.value = EditAccountUiState.Success
    }


    fun updateAccount(newName: String, newBalance: String) {
        val current = _account.value ?: return
        viewModelScope.launch {
            if (!networkMonitor.isConnected.value) {
                _uiState.value = EditAccountUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            try {
                val request = AccountUpdateRequestDto(
                    name = newName,
                    balance = newBalance,
                    currency = current.currency
                )
                val updatedAccount = repository.updateAccount(current.id, request)
                _account.value = updatedAccount
                _uiState.value = EditAccountUiState.Success
            } catch (e: Exception) {
                _uiState.value = EditAccountUiState.Error("Ошибка обновления: ${e.localizedMessage}")
            }
        }
    }

}
