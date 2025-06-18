package com.example.moneytalks.presentation.create_transaction

sealed class CreateTransactionIntent {
    object SubmitTransaction: CreateTransactionIntent()
    data class SetAccount(val id: Int): CreateTransactionIntent()
    data class SetCategory(val id: Int): CreateTransactionIntent()
    data class SetAmount(val amount: String): CreateTransactionIntent()
    data class SetDate(val date: String): CreateTransactionIntent()
    data class SetTime(val time: String): CreateTransactionIntent()
    data class SetComment(val id: String): CreateTransactionIntent()
}