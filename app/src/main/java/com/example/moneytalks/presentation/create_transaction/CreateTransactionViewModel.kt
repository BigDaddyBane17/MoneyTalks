package com.example.moneytalks.presentation.create_transaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.data.remote.model.Category
import com.example.moneytalks.data.remote.model.TransactionRequest
import com.example.moneytalks.domain.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class CreateTransactionViewModel(
    private val repository: BaseRepository,
    private val type: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateTransactionUiState>(CreateTransactionUiState.Loading)
    val uiState: StateFlow<CreateTransactionUiState> = _uiState.asStateFlow()

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    var selectedAccount: Account? = null
    var selectedCategory: Category? = null
    var amount: String = ""
    var date: String = ""
    var time: String = ""
    var comment: String = ""

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                val loadedAccounts = repository.getAccounts()
                val loadedCategories = repository.getCategoriesByType(isIncome = (type == "доходы"))
                _accounts.value = loadedAccounts
                _categories.value = loadedCategories
                _uiState.value = CreateTransactionUiState.Data(loadedAccounts, loadedCategories)
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
                val request = TransactionRequest(
                    accountId = acc.id,
                    categoryId = cat.id,
                    amount = amountString,
                    transactionDate = isoDateTime,
                    comment = commentToSend
                )
                Log.d("taaag", request.toString())
                repository.createTransaction(request)
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