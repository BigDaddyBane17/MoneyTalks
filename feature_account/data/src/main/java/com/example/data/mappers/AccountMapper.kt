package com.example.data.mappers

import com.example.core.data.entities.AccountEntity
import com.example.data.models.AccountDto
import com.example.core.domain.models.Account
import com.example.data.models.AccountResponseDto

fun AccountDto.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)
fun AccountEntity.toDomain(): Account = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)
fun Account.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)
fun AccountResponseDto.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)
