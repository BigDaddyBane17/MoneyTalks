package com.example.feature_expenses.ui.incomes.incomes_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_expenses.usecase.CreateTransactionUseCase
import com.example.feature_expenses.usecase.GetAccountsUseCase
import com.example.feature_expenses.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IncomesAddViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(IncomesAddState())
    val state: StateFlow<IncomesAddState> = _state.asStateFlow()

    init {
        handleIntent(IncomesAddIntent.LoadInitialData)
    }

    fun handleIntent(intent: IncomesAddIntent) {
        when (intent) {
            IncomesAddIntent.LoadInitialData -> loadInitialData()
            
            IncomesAddIntent.ToggleAccountDropdown -> toggleAccountDropdown()
            is IncomesAddIntent.SelectAccount -> selectAccount(intent.account)
            
            IncomesAddIntent.ToggleCategoryDropdown -> toggleCategoryDropdown()
            is IncomesAddIntent.SelectCategory -> selectCategory(intent.category)
            
            is IncomesAddIntent.AmountChanged -> updateAmount(intent.amount)
            
            IncomesAddIntent.ShowDatePicker -> showDatePicker()
            IncomesAddIntent.HideDatePicker -> hideDatePicker()
            is IncomesAddIntent.DateSelected -> updateDate(intent.date)
            
            IncomesAddIntent.ShowTimePicker -> showTimePicker()
            IncomesAddIntent.HideTimePicker -> hideTimePicker()
            is IncomesAddIntent.TimeSelected -> updateTime(intent.time)
            
            is IncomesAddIntent.CommentChanged -> updateComment(intent.comment)
            
            IncomesAddIntent.CreateTransaction -> createTransaction()
            
            IncomesAddIntent.ClearError -> clearError()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            try {
                val accounts = getAccountsUseCase()
                val categories = getCategoriesUseCase(isIncome = true)
                
                _state.value = _state.value.copy(
                    isLoading = false,
                    accounts = accounts,
                    categories = categories,
                    selectedAccount = accounts.firstOrNull()
                )
                
                validateForm()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки данных"
                )
            }
        }
    }

    private fun toggleAccountDropdown() {
        _state.value = _state.value.copy(
            isAccountDropdownExpanded = !_state.value.isAccountDropdownExpanded,
            isCategoryDropdownExpanded = false
        )
    }

    private fun selectAccount(account: com.example.core.domain.models.Account) {
        _state.value = _state.value.copy(
            selectedAccount = account,
            isAccountDropdownExpanded = false
        )
        validateForm()
    }

    private fun toggleCategoryDropdown() {
        _state.value = _state.value.copy(
            isCategoryDropdownExpanded = !_state.value.isCategoryDropdownExpanded,
            isAccountDropdownExpanded = false
        )
    }

    private fun selectCategory(category: com.example.core.domain.models.Category) {
        _state.value = _state.value.copy(
            selectedCategory = category,
            isCategoryDropdownExpanded = false
        )
        validateForm()
    }

    private fun updateAmount(amount: String) {
        val filteredAmount = amount.filter { it.isDigit() || it == '.' }
        
        val amountError = validateAmount(filteredAmount)
        
        _state.value = _state.value.copy(
            amount = filteredAmount,
            amountError = amountError
        )
        validateForm()
    }

    private fun validateAmount(amount: String): String? {
        if (amount.isBlank()) return "Введите сумму"
        
        return try {
            val value = BigDecimal(amount)
            when {
                value <= BigDecimal.ZERO -> "Сумма должна быть больше 0"
                value.scale() > 2 -> "Максимум 2 знака после запятой"
                else -> null
            }
        } catch (e: Exception) {
            "Неверный формат суммы"
        }
    }

    private fun showDatePicker() {
        _state.value = _state.value.copy(isDatePickerVisible = true)
    }

    private fun hideDatePicker() {
        _state.value = _state.value.copy(isDatePickerVisible = false)
    }

    private fun updateDate(date: LocalDate) {
        val currentTime = _state.value.selectedDateTime.toLocalTime()
        _state.value = _state.value.copy(
            selectedDateTime = LocalDateTime.of(date, currentTime),
            isDatePickerVisible = false
        )
    }

    private fun showTimePicker() {
        _state.value = _state.value.copy(isTimePickerVisible = true)
    }

    private fun hideTimePicker() {
        _state.value = _state.value.copy(isTimePickerVisible = false)
    }

    private fun updateTime(time: LocalTime) {
        val currentDate = _state.value.selectedDateTime.toLocalDate()
        _state.value = _state.value.copy(
            selectedDateTime = LocalDateTime.of(currentDate, time),
            isTimePickerVisible = false
        )
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

    private fun createTransaction() {
        val currentState = _state.value
        if (!currentState.isFormValid || currentState.isCreating) return

        val account = currentState.selectedAccount ?: return
        val category = currentState.selectedCategory ?: return
        
        viewModelScope.launch {
            _state.value = _state.value.copy(isCreating = true, error = null)
            
            try {
                val result = createTransactionUseCase(
                    accountId = account.id,
                    categoryId = category.id,
                    amount = currentState.amount,
                    transactionDate = currentState.selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z",
                    comment = currentState.comment.takeIf { it.isNotBlank() }
                )
                
                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isCreating = false,
                            isCreated = true
                        )
                    },
                    onFailure = { error ->
                        _state.value = _state.value.copy(
                            isCreating = false,
                            error = error.message ?: "Ошибка создания транзакции"
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isCreating = false,
                    error = e.message ?: "Ошибка создания транзакции"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}