package com.example.moneytalks.features.account.domain.model

import java.math.BigDecimal

data class AccountCreateRequest(
    val name: String,
    val balance: BigDecimal,
    val currency: String
)
