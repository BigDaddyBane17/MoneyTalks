package com.example.moneytalks.features.account.data.repository

import com.example.moneytalks.features.account.data.datasource.AccountRemoteDataSource
import com.example.moneytalks.features.account.data.mappers.toAccountBriefDomain
import com.example.moneytalks.features.account.data.mappers.toDomain
import com.example.moneytalks.features.account.data.mappers.toDto
import com.example.moneytalks.features.account.data.model.AccountUpdateRequestDto
import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.model.AccountBrief
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remote: AccountRemoteDataSource
): AccountRepository {
    override fun getAccounts(): Flow<List<Account>> = flow {
        val res = remote.getAccounts()
        emit(res.map { it.toDomain() })
    }

    override fun updateAccount(account: AccountBrief): Flow<AccountBrief> = flow {
        val request = AccountUpdateRequestDto(
            name = account.name,
            balance = account.balance.toPlainString(),
            currency = account.currency
        )
        val updated = remote.updateAccount(account.id, request)
        emit(updated.toAccountBriefDomain())
    }

}