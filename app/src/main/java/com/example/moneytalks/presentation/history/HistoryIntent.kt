package com.example.moneytalks.presentation.history

sealed class HistoryIntent {
    data class LoadHistory(
        val accountId: Int?,
        val startDate: String,
        val endDate: String
    ): HistoryIntent()
}