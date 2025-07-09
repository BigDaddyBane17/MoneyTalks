package com.example.moneytalks.features.account.domain.usecase

import com.example.moneytalks.features.account.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface GetAccountsUseCase {
    fun invoke(): Flow<List<Account>>
}
