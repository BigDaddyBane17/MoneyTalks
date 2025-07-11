package com.example.feature_expenses.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_expenses.ui.incomes.incomes_add.IncomesAddViewModel
import com.example.feature_expenses.ui.incomes.incomes_main.IncomesViewModel
import com.example.feature_expenses.ui.incomes.incomes_history.IncomesHistoryViewModel
import javax.inject.Inject
import javax.inject.Provider

class IncomesViewModelFactory @Inject constructor(
    private val incomesViewModelProvider: Provider<IncomesViewModel>,
    private val incomesAddViewModelProvider: Provider<IncomesAddViewModel>,
    private val incomesHistoryViewModelProvider: Provider<IncomesHistoryViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            IncomesViewModel::class.java -> incomesViewModelProvider.get() as T
            IncomesAddViewModel::class.java -> incomesAddViewModelProvider.get() as T
            IncomesHistoryViewModel::class.java -> incomesHistoryViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
} 