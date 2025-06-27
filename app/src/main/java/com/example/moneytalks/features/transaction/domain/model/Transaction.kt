package com.example.moneytalks.features.transaction.domain.model

import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.categories.domain.model.Category

data class Transaction(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val comment: String?,
    val transactionDate: String
)