package com.example.feature_expenses.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_expenses.ui.expenses.expenses_add.ExpensesAddViewModel
import com.example.feature_expenses.ui.expenses.expenses_analysis.ExpensesAnalysisViewModel
import com.example.feature_expenses.ui.expenses.expenses_main.ExpensesViewModel
import com.example.feature_expenses.ui.expenses.expenses_history.ExpensesHistoryViewModel
import com.example.feature_expenses.ui.incomes.incomes_analysis.IncomesAnalysisViewModel
import javax.inject.Inject
import javax.inject.Provider

class ExpensesViewModelFactory @Inject constructor(
    private val expensesViewModelProvider: Provider<ExpensesViewModel>,
    private val expensesAddViewModelProvider: Provider<ExpensesAddViewModel>,
    private val expensesHistoryViewModelProvider: Provider<ExpensesHistoryViewModel>,
    private val expensesAnalysisViewModelProvider: Provider<ExpensesAnalysisViewModel>,
    private val incomesAnalysisViewModelProvider: Provider<IncomesAnalysisViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ExpensesViewModel::class.java -> expensesViewModelProvider.get() as T
            ExpensesAddViewModel::class.java -> expensesAddViewModelProvider.get() as T
            ExpensesHistoryViewModel::class.java -> expensesHistoryViewModelProvider.get() as T
            ExpensesAnalysisViewModel::class.java -> expensesAnalysisViewModelProvider.get() as T
            IncomesAnalysisViewModel::class.java -> incomesAnalysisViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
} 