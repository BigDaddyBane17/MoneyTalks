package com.example.data.repository

import com.example.core.data.dao.AccountDao
import com.example.data.api.AccountApiService
import com.example.core.domain.models.Account
import com.example.core.domain.repository.AccountRepository
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import com.example.data.models.AccountUpdateRequestDto
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: AccountApiService,
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun getAccounts(): List<Account> {
        val cached = accountDao.getAll().map { it.toDomain() }
        return try {
            val remote = apiService.getAccounts()
            val entities = remote.map { it.toEntity() }
            accountDao.insertAll(entities)
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            cached
        }
    }

    override suspend fun getAccountById(id: Int): Account? {
        val cached = accountDao.getById(id)?.toDomain()
        return try {
            val response = apiService.getAccountById(id)
            val entity = response.toEntity()
            accountDao.insert(entity)
            entity.toDomain()
        } catch (e: Exception) {
            cached
        }
    }

    override suspend fun updateAccount(account: Account): Account {
        return try {
            val updateRequest = AccountUpdateRequestDto(
                name = account.name,
                currency = account.currency,
                balance = account.balance
            )
            val updatedDto = apiService.updateAccountById(account.id, updateRequest)
            val updatedEntity = updatedDto.toEntity()
            accountDao.insert(updatedEntity)
            updatedEntity.toDomain()
        } catch (e: Exception) {
            account
        }
    }

}