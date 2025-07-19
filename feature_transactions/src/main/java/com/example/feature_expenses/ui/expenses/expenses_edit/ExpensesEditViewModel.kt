package com.example.feature_expenses.ui.expenses.expenses_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Account
import com.example.core.domain.models.Category
import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.CategoryRepository
import com.example.domain.usecase.GetTransactionByIdUseCase
import com.example.domain.usecase.UpdateTransactionUseCase
import com.example.domain.usecase.DeleteTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesEditViewModel @Inject constructor(
    private val transactionId: Int,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpensesEditState(transactionId = transactionId))
    val state: StateFlow<ExpensesEditState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    fun handleIntent(intent: ExpensesEditIntent) {
        when (intent) {
            is ExpensesEditIntent.LoadTransaction -> loadTransaction()
            is ExpensesEditIntent.SelectAccount -> selectAccount(intent.account)
            is ExpensesEditIntent.SelectCategory -> selectCategory(intent.category)
            is ExpensesEditIntent.AmountChanged -> updateAmount(intent.amount)
            is ExpensesEditIntent.DateSelected -> updateDate(intent.date)
            is ExpensesEditIntent.TimeSelected -> updateTime(intent.time)
            is ExpensesEditIntent.CommentChanged -> updateComment(intent.comment)
            is ExpensesEditIntent.UpdateTransaction -> updateTransaction()
            is ExpensesEditIntent.DeleteTransaction -> deleteTransaction()
            is ExpensesEditIntent.ClearError -> clearError()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                // Показываем лоадер только если данных о транзакции ещё нет
                if (_state.value.amount.isBlank() && _state.value.selectedAccount == null && _state.value.selectedCategory == null) {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = null)
                }
                val accountsDeferred = async { accountRepository.getAccounts() }
                val categoriesDeferred = async { categoryRepository.getCategoriesByType(false) }
                val accounts = accountsDeferred.await()
                val categories = categoriesDeferred.await()
                _state.value = _state.value.copy(
                    accounts = accounts,
                    categories = categories
                )
                loadTransaction()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки данных: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun loadTransaction() {
        viewModelScope.launch {
            try {
                val result = getTransactionByIdUseCase(transactionId)
                result.fold(
                    onSuccess = { transaction ->
                        val selectedAccount = _state.value.accounts.find { 
                            it.name == transaction.accountName 
                        }
                        val selectedCategory = _state.value.categories.find { 
                            it.name == transaction.categoryName 
                        }
                        
                        _state.value = _state.value.copy(
                            isLoading = false,
                            selectedAccount = selectedAccount,
                            selectedCategory = selectedCategory,
                            amount = transaction.amount.removePrefix("-"),
                            selectedDateTime = transaction.transactionDate,
                            comment = transaction.comment ?: ""
                        )
                        validateForm()
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = "Ошибка загрузки транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun selectAccount(account: Account) {
        _state.value = _state.value.copy(selectedAccount = account)
        validateForm()
    }

    private fun selectCategory(category: Category) {
        _state.value = _state.value.copy(selectedCategory = category)
        validateForm()
    }

    private fun updateAmount(amount: String) {
        val sanitizedAmount = amount.replace(",", ".")
        val amountError = validateAmount(sanitizedAmount)
        
        _state.value = _state.value.copy(
            amount = sanitizedAmount,
            amountError = amountError
        )
        validateForm()
    }

    private fun validateAmount(amount: String): String? {
        return when {
            amount.isBlank() -> "Сумма не может быть пустой"
            amount.toDoubleOrNull() == null -> "Некорректная сумма"
            amount.toDouble() <= 0 -> "Сумма должна быть больше нуля"
            else -> null
        }
    }

    private fun updateDate(date: LocalDate) {
        val currentDateTime = _state.value.selectedDateTime
        val newDateTime = currentDateTime.with(date)
        _state.value = _state.value.copy(selectedDateTime = newDateTime)
    }

    private fun updateTime(time: LocalTime) {
        val currentDateTime = _state.value.selectedDateTime
        val newDateTime = currentDateTime.with(time)
        _state.value = _state.value.copy(selectedDateTime = newDateTime)
    }

    private fun updateComment(comment: String) {
        _state.value = _state.value.copy(comment = comment)
    }

    private fun validateForm() {
        val currentState = _state.value
        val isValid = currentState.selectedAccount != null &&
                currentState.selectedCategory != null &&
                currentState.amount.isNotBlank() &&
                currentState.amountError == null

        _state.value = currentState.copy(isFormValid = isValid)
    }

    private fun updateTransaction() {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isUpdating = true, error = null)

                val result = updateTransactionUseCase(
                    transactionId = transactionId,
                    accountId = currentState.selectedAccount!!.id,
                    categoryId = currentState.selectedCategory!!.id,
                    amount = currentState.amount,
                    transactionDate = currentState.selectedDateTime.atOffset(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    comment = currentState.comment.takeIf { it.isNotBlank() }
                )

                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isUpdating = false,
                            isUpdated = true
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isUpdating = false,
                            error = "Ошибка обновления транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isUpdating = false,
                    error = "Ошибка обновления транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun deleteTransaction() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isDeleting = true, error = null)

                val result = deleteTransactionUseCase(transactionId)

                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isDeleting = false,
                            isDeleted = true
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isDeleting = false,
                            error = "Ошибка удаления транзакции: ${error.localizedMessage ?: error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isDeleting = false,
                    error = "Ошибка удаления транзакции: ${e.localizedMessage ?: e.message}"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
} 