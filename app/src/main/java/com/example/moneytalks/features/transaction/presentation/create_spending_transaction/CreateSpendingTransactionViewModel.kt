package com.example.moneytalks.features.transaction.presentation.create_spending_transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import com.example.moneytalks.features.transaction.data.remote.model.TransactionRequestDto
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.core.network.retryIO
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import com.example.moneytalks.features.categories.domain.repository.CategoryRepository
import com.example.moneytalks.features.transaction.domain.repository.TransactionRepository
import com.example.moneytalks.features.transaction.presentation.create_earning_transaction.CreateEarningTransactionIntent
import com.example.moneytalks.features.transaction.presentation.create_earning_transaction.CreateEarningTransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateSpendingTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateSpendingTransactionUiState>(CreateSpendingTransactionUiState.Loading)
    val uiState: StateFlow<CreateSpendingTransactionUiState> = _uiState.asStateFlow()

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
                _uiState.value = CreateSpendingTransactionUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            try {
                val loadedAccounts = retryIO(times = 3, delayMillis = 2000){
                    accountRepository.getAccounts()
                }
                val loadedCategories = retryIO(times = 3, delayMillis = 2000){
                    categoryRepository.getCategoriesByType(isIncome = false)
                }
                _accounts.value = loadedAccounts
                _categories.value = loadedCategories
                _uiState.value = CreateSpendingTransactionUiState.Data(loadedAccounts, loadedCategories)
            } catch (e: IOException) {
                _uiState.value = CreateSpendingTransactionUiState.Error("Нет соединения с интернетом")
            } catch (e: Exception) {
                _uiState.value = CreateSpendingTransactionUiState.Error("Ошибка загрузки: ${e.localizedMessage}")
            }
        }
    }



    fun handleIntent(intent: CreateSpendingTransactionIntent) {
        when (intent) {
            is CreateSpendingTransactionIntent.SetAccount -> {
                selectedAccount = _accounts.value.firstOrNull { it.id == intent.id }
            }
            is CreateSpendingTransactionIntent.SetCategory -> {
                selectedCategory = _categories.value.firstOrNull { it.id == intent.id }
            }
            is CreateSpendingTransactionIntent.SetAmount -> {
                amount = intent.amount
            }
            is CreateSpendingTransactionIntent.SetDate -> {
                date = intent.date
            }
            is CreateSpendingTransactionIntent.SetTime -> {
                time = intent.time
            }
            is CreateSpendingTransactionIntent.SetComment -> {
                comment = intent.id
            }
            CreateSpendingTransactionIntent.SubmitTransaction -> {
                submit()
            }
        }
    }

    private fun submit() {
        val acc = selectedAccount
        val cat = selectedCategory

        if (acc == null || cat == null || amount.isBlank() || date.isBlank() || time.isBlank()) {
            _uiState.value = CreateSpendingTransactionUiState.Error("Заполните все обязательные поля")
            return
        }

        val isoDateTime = try {
            val localDate = LocalDate.parse(date)
            val (h, m) = time.split(":").map { it.toInt() }
            val localDateTime = LocalDateTime.of(localDate, LocalTime.of(h, m))
            localDateTime.atZone(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
        } catch (e: Exception) {
            _uiState.value = CreateSpendingTransactionUiState.Error("Некорректная дата или время")
            return
        }

        val amountString = amount.toDoubleOrNull()?.let { String.format(Locale.US, "%.2f", it) } ?: run {
            _uiState.value = CreateSpendingTransactionUiState.Error("Некорректная сумма")
            return
        }

        val commentToSend = if (comment.isBlank()) null else comment

        _uiState.value = CreateSpendingTransactionUiState.Loading
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
                    transactionRepository.createTransaction(request)
                }
                _uiState.value = CreateSpendingTransactionUiState.Success
            } catch (e: Exception) {
                _uiState.value = CreateSpendingTransactionUiState.Error("Ошибка при создании: ${e.localizedMessage}")
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

        _uiState.value = CreateSpendingTransactionUiState.Loading
        loadInitialData()
    }

}