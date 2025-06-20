package com.example.moneytalks.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.network.NetworkMonitor

class TransactionViewModelFactory(
    private val repository: BaseRepository,
    private val type: String,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(repository, type, networkMonitor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
