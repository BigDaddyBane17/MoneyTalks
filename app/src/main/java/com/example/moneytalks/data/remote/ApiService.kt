package com.example.moneytalks.data.remote

import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.data.remote.model.AccountCreateRequest
import com.example.moneytalks.data.remote.model.AccountHistoryResponse
import com.example.moneytalks.data.remote.model.AccountResponse
import com.example.moneytalks.data.remote.model.AccountUpdateRequest
import com.example.moneytalks.data.remote.model.Category
import com.example.moneytalks.data.remote.model.Transaction
import com.example.moneytalks.data.remote.model.TransactionRequest
import com.example.moneytalks.data.remote.model.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("accounts")
    suspend fun getAccounts(): List<Account>

    @POST("accounts")
    suspend fun createAccount(@Body request: AccountCreateRequest): Account

    @GET("account/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponse

    @PUT("account/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body request: AccountUpdateRequest
    ): Account

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(@Path("id") id: Int)

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(@Path("id") id: Int): AccountHistoryResponse


    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<Category>

    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequest): Transaction

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): TransactionResponse

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequest
    ): TransactionResponse

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Unit

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): List<TransactionResponse>

}