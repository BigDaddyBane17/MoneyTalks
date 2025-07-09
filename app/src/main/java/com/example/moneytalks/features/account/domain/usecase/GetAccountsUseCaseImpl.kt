package com.example.moneytalks.features.account.domain.usecase

import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : GetAccountsUseCase {
    override fun invoke(): Flow<List<Account>> = repository.getAccounts()
}
