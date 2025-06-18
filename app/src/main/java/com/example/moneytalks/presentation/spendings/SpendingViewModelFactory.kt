package com.example.moneytalks.presentation.spendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytalks.domain.repository.BaseRepository

class SpendingViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpendingViewModel::class.java)) {
            return SpendingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
