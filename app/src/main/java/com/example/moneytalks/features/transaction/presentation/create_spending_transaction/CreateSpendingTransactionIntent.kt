package com.example.moneytalks.features.transaction.presentation.create_spending_transaction

sealed class CreateSpendingTransactionIntent {
    object SubmitTransaction: CreateSpendingTransactionIntent()
    data class SetAccount(val id: Int): CreateSpendingTransactionIntent()
    data class SetCategory(val id: Int): CreateSpendingTransactionIntent()
    data class SetAmount(val amount: String): CreateSpendingTransactionIntent()
    data class SetDate(val date: String): CreateSpendingTransactionIntent()
    data class SetTime(val time: String): CreateSpendingTransactionIntent()
    data class SetComment(val id: String): CreateSpendingTransactionIntent()
}