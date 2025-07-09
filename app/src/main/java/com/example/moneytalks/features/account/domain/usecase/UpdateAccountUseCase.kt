package com.example.moneytalks.features.account.domain.usecase

import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.model.AccountBrief
import kotlinx.coroutines.flow.Flow

interface UpdateAccountUseCase {
    suspend fun invoke(account: AccountBrief): Flow<AccountBrief>
}
