package com.example.moneytalks.presentation.create_transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.data.remote.model.CategoryDto
import com.example.moneytalks.data.remote.model.TransactionRequestDto
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.network.NetworkMonitor
import com.example.moneytalks.network.retryIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

class CreateTransactionViewModel(
    private val repository: BaseRepository,
    private val type: String,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateTransactionUiState>(CreateTransactionUiState.Loading)
    val uiState: StateFlow<CreateTransactionUiState> = _uiState.asStateFlow()

    private val _accounts = MutableStateFlow<List<AccountDto>>(emptyList())
    val accounts: StateFlow<List<AccountDto>> = _accounts.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    val categories: StateFlow<List<CategoryDto>> = _categories.asStateFlow()

    var selectedAccount: AccountDto? = null
    var selectedCategory: CategoryDto? = null
    var amount: String = ""
    var date: String = ""
    var time: String = ""
    var comment: String = ""

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            if (!networkMonitor.isConnected.value) {
                _uiState.value = CreateTransactionUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            try {
                val loadedAccounts = retryIO(times = 3, delayMillis = 2000){
                    repository.getAccounts()
                }
                val loadedCategories = retryIO(times = 3, delayMillis = 2000){
                    repository.getCategoriesByType(isIncome = (type == "доходы"))
                }
                _accounts.value = loadedAccounts
                _categories.value = loadedCategories
                _uiState.value = CreateTransactionUiState.Data(loadedAccounts, loadedCategories)
            } catch (e: IOException) {
                _uiState.value = CreateTransactionUiState.Error("Нет соединения с интернетом")
            } catch (e: Exception) {
                _uiState.value = CreateTransactionUiState.Error("Ошибка загрузки: ${e.localizedMessage}")
            }
        }
    }



    fun handleIntent(intent: CreateTransactionIntent) {
        when (intent) {
            is CreateTransactionIntent.SetAccount -> {
                selectedAccount = _accounts.value.firstOrNull { it.id == intent.id }
            }
            is CreateTransactionIntent.SetCategory -> {
                selectedCategory = _categories.value.firstOrNull { it.id == intent.id }
            }
            is CreateTransactionIntent.SetAmount -> {
                amount = intent.amount
            }
            is CreateTransactionIntent.SetDate -> {
                date = intent.date
            }
            is CreateTransactionIntent.SetTime -> {
                time = intent.time
            }
            is CreateTransactionIntent.SetComment -> {
                comment = intent.id
            }
            CreateTransactionIntent.SubmitTransaction -> {
                submit()
            }
        }
    }

    private fun submit() {
        val acc = selectedAccount
        val cat = selectedCategory

        if (acc == null || cat == null || amount.isBlank() || date.isBlank() || time.isBlank()) {
            _uiState.value = CreateTransactionUiState.Error("Заполните все обязательные поля")
            return
        }

        val isoDateTime = try {
            val localDate = java.time.LocalDate.parse(date)
            val (h, m) = time.split(":").map { it.toInt() }
            val localDateTime = java.time.LocalDateTime.of(localDate, java.time.LocalTime.of(h, m))
            localDateTime.atZone(java.time.ZoneOffset.UTC)
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
        } catch (e: Exception) {
            _uiState.value = CreateTransactionUiState.Error("Некорректная дата или время")
            return
        }

        val amountString = amount.toDoubleOrNull()?.let { String.format(Locale.US, "%.2f", it) } ?: run {
            _uiState.value = CreateTransactionUiState.Error("Некорректная сумма")
            return
        }

        val commentToSend = if (comment.isBlank()) null else comment

        _uiState.value = CreateTransactionUiState.Loading
        viewModelScope.launch {
            try {
                val request = TransactionRequestDto(
                    accountId = acc.id,
                    categoryId = cat.id,
                    amount = amountString,
                    transactionDate = isoDateTime,
                    comment = commentToSend
                )
                Log.d("taaag", request.toString())
                retryIO(times = 3, delayMillis = 2000) {
                    repository.createTransaction(request)
                }
                _uiState.value = CreateTransactionUiState.Success
            } catch (e: Exception) {
                _uiState.value = CreateTransactionUiState.Error("Ошибка при создании: ${e.localizedMessage}")
            }
        }

    }

    fun reset() {
        selectedAccount = null
        selectedCategory = null
        amount = ""
        date = ""
        time = ""
        comment = ""

        _uiState.value = CreateTransactionUiState.Loading
        loadInitialData()
    }

}