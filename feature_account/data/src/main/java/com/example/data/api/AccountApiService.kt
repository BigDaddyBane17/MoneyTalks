package com.example.data.api


import com.example.data.models.AccountCreateRequestDto
import com.example.data.models.AccountDto
import com.example.data.models.AccountHistoryResponseDto
import com.example.data.models.AccountResponseDto
import com.example.data.models.AccountUpdateRequestDto
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

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponseDto

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body request: AccountUpdateRequestDto
    ): AccountDto

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(@Path("id") id: Int)

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(@Path("id") id: Int): AccountHistoryResponseDto
}