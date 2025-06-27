package com.example.moneytalks.core.di

import android.content.Context

import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.core.network.AuthInterceptor

import com.example.moneytalks.features.account.data.remote.AccountApiService
import com.example.moneytalks.features.categories.data.remote.CategoryApiService
import com.example.moneytalks.features.transaction.data.remote.TransactionApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideInterceptor(): Interceptor = AuthInterceptor()

    @Provides
    fun provideBaseUrl(): String = "https://shmr-finance.ru/api/v1/"

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitor(context)
}