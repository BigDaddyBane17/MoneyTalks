package com.example.moneytalks.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue


object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}