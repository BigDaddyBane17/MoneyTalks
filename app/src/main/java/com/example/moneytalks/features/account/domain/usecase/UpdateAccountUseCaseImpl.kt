package com.example.moneytalks.features.account.domain.usecase

import com.example.moneytalks.features.account.domain.model.AccountBrief
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAccountUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
): UpdateAccountUseCase {
    override suspend fun invoke(account: AccountBrief): Flow<AccountBrief> {
        return repository.updateAccount(account)
    }

}