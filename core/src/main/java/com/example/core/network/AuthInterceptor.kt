package com.example.core.network

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
            .addHeader("Authorization", "Bearer oEo6HAPP3RZ8glq8oxlcfnwB")
            .build()
        return chain.proceed(request)
    }
}