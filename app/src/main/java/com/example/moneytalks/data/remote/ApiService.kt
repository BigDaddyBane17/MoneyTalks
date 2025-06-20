package com.example.moneytalks.data.remote

import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.data.remote.model.AccountHistoryResponseDto
import com.example.moneytalks.data.remote.model.AccountResponseDto
import com.example.moneytalks.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.data.remote.model.CategoryDto
import com.example.moneytalks.data.remote.model.TransactionDto
import com.example.moneytalks.data.remote.model.TransactionRequestDto
import com.example.moneytalks.data.remote.model.TransactionResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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


    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDto>

    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequestDto): TransactionDto

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): TransactionResponseDto

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequestDto
    ): TransactionResponseDto

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int)

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponseDto>

}