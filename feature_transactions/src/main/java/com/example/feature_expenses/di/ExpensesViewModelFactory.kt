package com.example.feature_expenses.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesViewModel
import javax.inject.Inject
import javax.inject.Provider

class ExpensesViewModelFactory @Inject constructor(
    private val expensesViewModelProvider: Provider<ExpensesViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ExpensesViewModel::class.java -> expensesViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
} 