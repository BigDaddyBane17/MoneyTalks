package com.example.moneytalks.features.account.domain.repository

import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.model.AccountBrief
import com.example.moneytalks.features.account.domain.model.AccountCreateRequest
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<List<Account>>
    fun updateAccount(account: AccountBrief): Flow<AccountBrief>
}