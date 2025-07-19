package com.example.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.usecase.SyncTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncViewModel @Inject constructor(
    private val syncTransactionsUseCase: SyncTransactionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SyncUiState())
    val uiState: StateFlow<SyncUiState> = _uiState.asStateFlow()

    init {
        checkUnsyncedData()
    }

    fun syncTransactions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSyncing = true)
            
            try {
                val syncedCount = syncTransactionsUseCase.execute()
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    hasUnsyncedData = false,
                    lastSyncResult = "Синхронизировано $syncedCount транзакций"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSyncing = false,
                    lastSyncResult = "Ошибка синхронизации: ${e.message}"
                )
            }
        }
    }

    fun checkUnsyncedData() {
        viewModelScope.launch {
            try {
                val hasUnsynced = syncTransactionsUseCase.hasUnsyncedTransactions()
                _uiState.value = _uiState.value.copy(hasUnsyncedData = hasUnsynced)
            } catch (e: Exception) {
                // Игнорируем ошибки при проверке
            }
        }
    }

    fun clearLastSyncResult() {
        _uiState.value = _uiState.value.copy(lastSyncResult = null)
    }
}

data class SyncUiState(
    val isSyncing: Boolean = false,
    val hasUnsyncedData: Boolean = false,
    val lastSyncResult: String? = null
) 