package com.example.moneytalks.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytalks.domain.repository.BaseRepository

class TransactionViewModelFactory(
    private val repository: BaseRepository,
    private val type: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(repository, type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
