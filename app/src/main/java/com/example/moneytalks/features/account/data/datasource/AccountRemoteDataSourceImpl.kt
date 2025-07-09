package com.example.moneytalks.features.account.data.datasource

import com.example.moneytalks.features.account.data.api.AccountApiService
import com.example.moneytalks.features.account.data.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.model.AccountDto
import com.example.moneytalks.features.account.data.model.AccountUpdateRequestDto

/**
 * Реализация репозитория для работы с аккаунтами через API.
 */


class AccountRemoteDataSourceImpl(
    private val api: AccountApiService
): AccountRemoteDataSource {
    override suspend fun getAccounts() = api.getAccounts()
    override suspend fun getAccountById(id: Int) = api.getAccountById(id)
    override suspend fun createAccount(request: AccountCreateRequestDto) = api.createAccount(request)
    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestDto): AccountDto = api.updateAccountById(id, request)
    override suspend fun deleteAccount(id: Int) = api.deleteAccountById(id)
    override suspend fun getAccountHistory(id: Int) = api.getAccountHistory(id)
}