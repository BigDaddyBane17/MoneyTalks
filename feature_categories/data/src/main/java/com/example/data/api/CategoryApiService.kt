package com.example.data.api

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDto>
}