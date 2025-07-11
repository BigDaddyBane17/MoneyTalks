package com.example.core.repository

import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.prefs.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedAccountRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : SelectedAccountRepository {

    private val _selectedAccountIdFlow = MutableStateFlow<Int?>(null)
    override val selectedAccountIdFlow: StateFlow<Int?> = _selectedAccountIdFlow.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            _selectedAccountIdFlow.value = userPreferences.getSelectedAccountId()
        }
    }

    override suspend fun setSelectedAccountId(accountId: Int) {
        userPreferences.setSelectedAccountId(accountId)
        _selectedAccountIdFlow.value = accountId
    }

    override suspend fun getSelectedAccountId(): Int? {
        return userPreferences.getSelectedAccountId()
    }

    override suspend fun clearSelectedAccount() {
        userPreferences.clearSelectedAccount()
        _selectedAccountIdFlow.value = null
    }
} 