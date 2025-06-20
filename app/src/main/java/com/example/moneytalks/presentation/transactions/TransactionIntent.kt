package com.example.moneytalks.presentation.transactions

sealed class TransactionIntent {
    data class LoadExpenses(
       val accountId: Int?, val startDate: String, val endDate: String
    ): TransactionIntent()
    object OnItemClicked: TransactionIntent()
}