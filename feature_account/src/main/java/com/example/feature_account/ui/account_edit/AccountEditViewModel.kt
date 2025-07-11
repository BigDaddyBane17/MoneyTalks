package com.example.feature_account.ui.account_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.usecase.GetCurrentAccountUseCase
import com.example.feature_account.domain.usecase.EditAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class AccountEditViewModel @Inject constructor(
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
    private val editAccountUseCase: EditAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountEditState())
    val state: StateFlow<AccountEditState> = _state.asStateFlow()

    init {
        handleIntent(AccountEditIntent.LoadAccount)
    }

    fun handleIntent(intent: AccountEditIntent) {
        when (intent) {
            is AccountEditIntent.LoadAccount -> loadAccount()
            is AccountEditIntent.NameChanged -> updateName(intent.name)
            is AccountEditIntent.BalanceChanged -> updateBalance(intent.balance)
            is AccountEditIntent.SaveAccount -> saveAccount()
            is AccountEditIntent.ClearError -> clearError()
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val account = getCurrentAccountUseCase.getCurrentAccount()
                if (account != null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        account = account,
                        name = account.name,
                        balance = account.balance,
                        currency = account.currency,
                        isFormValid = true
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить счет"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки счета"
                )
            }
        }
    }

    private fun updateName(name: String) {
        val nameError = validateName(name)
        _state.value = _state.value.copy(
            name = name,
            nameError = nameError,
            isFormValid = nameError == null && _state.value.balanceError == null
        )
    }

    private fun updateBalance(balance: String) {
        val balanceError = validateBalance(balance)
        _state.value = _state.value.copy(
            balance = balance,
            balanceError = balanceError,
            isFormValid = _state.value.nameError == null && balanceError == null
        )
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Название счета не может быть пустым"
            name.length < 2 -> "Название должно содержать минимум 2 символа"
            name.length > 50 -> "Название не должно превышать 50 символов"
            else -> null
        }
    }

    private fun validateBalance(balance: String): String? {
        return try {
            val amount = BigDecimal(balance)
            when {
                amount.scale() > 2 -> "Баланс не может иметь больше 2 знаков после запятой"
                else -> null
            }
        } catch (e: NumberFormatException) {
            "Некорректный формат баланса"
        }
    }

    private fun saveAccount() {
        if (!_state.value.isFormValid || _state.value.account == null) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, error = null)
            try {
                val account = _state.value.account!!
                editAccountUseCase(
                    accountId = account.id,
                    name = _state.value.name,
                    balance = _state.value.balance,
                    currency = account.currency
                )
                _state.value = _state.value.copy(
                    isSaving = false,
                    isSaved = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSaving = false,
                    error = e.message ?: "Ошибка сохранения счета"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}