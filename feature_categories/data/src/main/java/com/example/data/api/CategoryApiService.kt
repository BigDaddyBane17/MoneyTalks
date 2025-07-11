package com.example.data.api

import com.example.core.data.models.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDto>
}