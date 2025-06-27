package com.example.moneytalks.features.categories.data.remote

import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): List<CategoryDto>
}
