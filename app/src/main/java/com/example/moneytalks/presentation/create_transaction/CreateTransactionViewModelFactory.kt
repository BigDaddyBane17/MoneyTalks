package com.example.moneytalks.presentation.create_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytalks.domain.repository.BaseRepository


class CreateTransactionViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTransactionViewModel::class.java)) {
            return CreateTransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}