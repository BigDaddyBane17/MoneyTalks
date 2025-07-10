package com.example.data.repository

import com.example.data.api.AccountApiService
import com.example.data.mappers.AccountMapper
import com.example.domain.models.Account
import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val apiService: AccountApiService,
    private val mapper: AccountMapper
) : AccountRepository {
    
    override suspend fun getAccounts(): List<Account> {
        return apiService.getAccounts().map { mapper.toDomain(it) }
    }
}