package com.example.moneytalks.features.account.data.mappers

import com.example.moneytalks.features.account.data.model.AccountBriefDto
import com.example.moneytalks.features.account.data.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.model.AccountDto
import com.example.moneytalks.features.account.data.model.AccountHistoryDto
import com.example.moneytalks.features.account.data.model.AccountHistoryResponseDto
import com.example.moneytalks.features.account.data.model.AccountStateDto
import com.example.moneytalks.features.account.data.model.AccountUpdateRequestDto
import com.example.moneytalks.features.account.domain.model.Account
import com.example.moneytalks.features.account.domain.model.AccountBrief
import com.example.moneytalks.features.account.domain.model.AccountCreateRequest
import com.example.moneytalks.features.account.domain.model.AccountHistory
import com.example.moneytalks.features.account.domain.model.AccountHistoryResponse
import com.example.moneytalks.features.account.domain.model.AccountState


fun Account.toAccountBrief(): AccountBrief = AccountBrief(
    id = this.id,
    name = this.name,
    balance = this.balance.toBigDecimal(),
    currency = this.currency
)


fun AccountDto.toDomain(): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun AccountBriefDto.toDomain(): AccountBrief = AccountBrief(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency
)

fun AccountCreateRequest.toDto(): AccountCreateRequestDto = AccountCreateRequestDto(
    name = name,
    balance = balance.toPlainString(),
    currency = currency
)

fun AccountUpdateRequestDto.toDomain(id: Int): AccountBrief = AccountBrief(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency
)

fun AccountBrief.toDto(): AccountBriefDto = AccountBriefDto(
    id = id,
    name = name,
    balance = balance.toPlainString(),
    currency = currency
)


fun AccountHistoryDto.toDomain(): AccountHistory = AccountHistory(
    id = id,
    accountId = accountId,
    changeType = changeType,
    previousState = previousState?.toDomain(),
    newState = newState.toDomain(),
    changeTimestamp = changeTimestamp,
    createdAt = createdAt
)


fun AccountStateDto.toDomain(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)


fun AccountHistoryResponseDto.toDomain(): AccountHistoryResponse = AccountHistoryResponse(
    accountId = accountId,
    accountName = accountName,
    currency = currency,
    currentBalance = currentBalance,
    history = history.map { it.toDomain() }
)

fun AccountDto.toAccountBriefDomain(): AccountBrief = AccountBrief(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency
)

