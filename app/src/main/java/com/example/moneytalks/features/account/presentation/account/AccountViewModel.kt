package com.example.moneytalks.features.account.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.model.AccountBrief
import com.example.moneytalks.features.account.domain.usecase.GetAccountsUseCase
import com.example.moneytalks.features.account.domain.usecase.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для получения, выбора и отображения счеирв, а также обработки состояния загрузки/ошибок.
 */

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _selectedAccountId = MutableStateFlow<Int?>(null)
    val selectedAccountId: StateFlow<Int?> = _selectedAccountId.asStateFlow()

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    val currency: StateFlow<String> = combine(accounts, selectedAccountId) { accounts, id ->
        val acc = id?.let { selId -> accounts.find { it.id == selId } } ?: accounts.firstOrNull()
        acc?.currency ?: "₽"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "₽")

    init {
        viewModelScope.launch {
            getAccountsUseCase.invoke()
                .catch { ex -> _uiState.value = AccountUiState.Error(ex.message ?: "Ошибка") }
                .collect { list ->
                    _accounts.value = list
                }
        }

        viewModelScope.launch {
            combine(_accounts, _selectedAccountId) { list, id ->
                if (list.isEmpty()) {
                    AccountUiState.Loading
                } else {
                    val acc = id?.let { selId -> list.find { it.id == selId } } ?: list.first()
                    AccountUiState.Success(acc)
                }
            }.catch { ex ->
                _uiState.value = AccountUiState.Error(ex.message ?: "Ошибка")
            }.collect { _uiState.value = it }
        }
    }

    fun selectAccount(accountId: Int) {
        _selectedAccountId.value = accountId
    }

    fun handleIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.CurrencyClick -> {
                viewModelScope.launch {
                    val account = _accounts.value.find { it.id == intent.accountId }
                    if (account != null) {
                        val updated = AccountBrief(
                            id = account.id,
                            name = account.name,
                            balance = account.balance.toBigDecimal(),
                            currency = intent.currency
                        )
                        updateAccountUseCase.invoke(updated).collect { newAccount ->
                            _accounts.update { list ->
                                list.map { acc ->
                                    if (acc.id == newAccount.id) {
                                        acc.copy(currency = newAccount.currency)
                                    } else acc
                                }
                            }
                        }
                    }
                }
            }
            is AccountIntent.SelectAccount -> {
                selectAccount(intent.accountId)
            }
            is AccountIntent.AccountEdit -> {
                viewModelScope.launch {
                    updateAccountUseCase.invoke(intent.account).collect { newAccount ->
                        _accounts.update { list ->
                            list.map { acc ->
                                if (acc.id == newAccount.id) {
                                    acc.copy(
                                        name = newAccount.name,
                                        balance = newAccount.balance.toString(),
                                        currency = newAccount.currency
                                    )
                                } else acc
                            }
                        }
                    }
                }
            }
        }
    }
}

