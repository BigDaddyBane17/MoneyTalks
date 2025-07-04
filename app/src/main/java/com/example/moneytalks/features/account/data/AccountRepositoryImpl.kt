package com.example.moneytalks.features.account.data

import com.example.moneytalks.features.account.data.remote.AccountApiService
import com.example.moneytalks.features.account.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.account.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.features.account.domain.repository.AccountRepository

/**
 * Реализация репозитория для работы с аккаунтами через API.
 */

class AccountRepositoryImpl(
    private val api: AccountApiService
): AccountRepository {
    override suspend fun getAccounts() = api.getAccounts()
    override suspend fun getAccountById(id: Int) = api.getAccountById(id)
    override suspend fun createAccount(request: AccountCreateRequestDto) = api.createAccount(request)
    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestDto): AccountDto = api.updateAccountById(id, request)
    override suspend fun deleteAccount(id: Int) = api.deleteAccountById(id)
    override suspend fun getAccountHistory(id: Int) = api.getAccountHistory(id)
}
