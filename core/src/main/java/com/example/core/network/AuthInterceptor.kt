package com.example.core.network

import android.util.Log
import com.example.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Интерцептор для автоматического добавления заголовка Authorization
 * с Bearer-токеном ко всем HTTP-запросам.
 */

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            .build()
        
        Log.d("AuthInterceptor", "HTTP $request")
        
        val response = chain.proceed(request)
        
        Log.d("AuthInterceptor", "HTTP $response")
        
        return response
    }
}