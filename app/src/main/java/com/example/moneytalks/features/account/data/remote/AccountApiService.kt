package com.example.moneytalks.features.account.data.remote

import com.example.moneytalks.features.account.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.account.data.remote.model.AccountHistoryResponseDto
import com.example.moneytalks.features.account.data.remote.model.AccountResponseDto
import com.example.moneytalks.features.account.data.remote.model.AccountUpdateRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>

    @POST("accounts")
    suspend fun createAccount(@Body request: AccountCreateRequestDto): AccountDto

    @GET("account/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponseDto

    @PUT("account/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body request: AccountUpdateRequestDto
    ): AccountDto

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(@Path("id") id: Int)

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(@Path("id") id: Int): AccountHistoryResponseDto
}
