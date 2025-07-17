package com.example.data.repository

import com.example.data.api.AccountApiService
import com.example.data.mappers.AccountMapper
import com.example.core.domain.models.Account
import com.example.core.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: AccountApiService,
    private val mapper: AccountMapper
) : AccountRepository {
    
    override suspend fun getAccounts(): List<Account> {
        return try {
            apiService.getAccounts().map { mapper.toDomain(it) }
        } catch (e: Exception) {
            // Возвращаем пустой список при сетевых ошибках
            emptyList()
        }
    }
    
    override suspend fun getAccountById(id: Int): Account? {
        return try {
            val response = apiService.getAccountById(id)
            Account(
                id = response.id,
                name = response.name,
                balance = response.balance,
                currency = response.currency
            )
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun updateAccount(account: Account): Account {
        return try {
            val updateRequest = com.example.data.models.AccountUpdateRequestDto(
                name = account.name,
                currency = account.currency,
                balance = account.balance
            )
            val updatedDto = apiService.updateAccountById(account.id, updateRequest)
            Account(
                id = updatedDto.id,
                name = updatedDto.name,
                balance = updatedDto.balance,
                currency = updatedDto.currency
            )
        } catch (e: Exception) {
            // Возвращаем исходный аккаунт при ошибке
            account
        }
    }

}