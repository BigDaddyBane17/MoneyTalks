package com.example.moneytalks.features.transaction.presentation.create_earning_transaction

sealed class CreateEarningTransactionIntent {
    object SubmitTransaction: CreateEarningTransactionIntent()
    data class SetAccount(val id: Int): CreateEarningTransactionIntent()
    data class SetCategory(val id: Int): CreateEarningTransactionIntent()
    data class SetAmount(val amount: String): CreateEarningTransactionIntent()
    data class SetDate(val date: String): CreateEarningTransactionIntent()
    data class SetTime(val time: String): CreateEarningTransactionIntent()
    data class SetComment(val id: String): CreateEarningTransactionIntent()
}