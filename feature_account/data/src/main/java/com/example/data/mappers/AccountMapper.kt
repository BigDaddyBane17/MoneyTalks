package com.example.data.mappers

import com.example.data.models.AccountDto
import com.example.domain.models.Account
import javax.inject.Inject

class AccountMapper @Inject constructor() {
    
    fun toDomain(dto: AccountDto): Account {
        return Account(
            id = dto.id,
            name = dto.name,
            balance = dto.balance,
            currency = dto.currency
        )
    }
} 