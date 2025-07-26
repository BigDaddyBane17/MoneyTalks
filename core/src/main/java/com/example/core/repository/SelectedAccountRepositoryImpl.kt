package com.example.core.repository

import com.example.core.domain.models.Account
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

    private val _selectedAccountFlow = MutableStateFlow<Account?>(null)
    override val selectedAccountFlow: StateFlow<Account?> = _selectedAccountFlow.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            _selectedAccountFlow.value = userPreferences.getSelectedAccount()
        }
    }

    override suspend fun setSelectedAccount(account: Account) {
        userPreferences.setSelectedAccount(account)
        _selectedAccountFlow.value = account
    }

    override suspend fun getSelectedAccount(): Account? {
        return _selectedAccountFlow.value
    }

    override suspend fun clearSelectedAccount() {
        _selectedAccountFlow.value = null
    }
} 