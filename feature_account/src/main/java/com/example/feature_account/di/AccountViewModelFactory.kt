package com.example.feature_account.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_account.ui.account_main.AccountViewModel
import com.example.feature_account.ui.account_edit.AccountEditViewModel
import javax.inject.Inject
import javax.inject.Provider

class AccountViewModelFactory @Inject constructor(
    private val accountViewModelProvider: Provider<AccountViewModel>,
    private val accountEditViewModelProvider: Provider<AccountEditViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AccountViewModel::class.java -> accountViewModelProvider.get() as T
            AccountEditViewModel::class.java -> accountEditViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}