package com.example.moneytalks.presentation.spendings

sealed class SpendingIntent {
    data class LoadExpenses(
       val accountId: Int, val startDate: String, val endDate: String
    ): SpendingIntent()
    object OnItemClicked: SpendingIntent()
}