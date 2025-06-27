package com.example.moneytalks.core.network

import com.example.moneytalks.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


/**
 * Интерцептор для автоматического добавления заголовка Authorization
 * с Bearer-токеном ко всем HTTP-запросам.
 */

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}
