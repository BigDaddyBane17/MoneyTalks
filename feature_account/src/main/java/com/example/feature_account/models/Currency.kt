package com.example.feature_account.models

import androidx.annotation.DrawableRes

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    @DrawableRes val iconRes: Int
) 