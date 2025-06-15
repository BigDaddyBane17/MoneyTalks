package com.example.moneytalks.presentation.navigation

import com.example.moneytalks.R

data class BottomNavigationItem(
    val title: String,
    val icon: Int
)

val items = listOf(
    BottomNavigationItem("Расходы", R.drawable.spendings),
    BottomNavigationItem("Доходы", R.drawable.earnings),
    BottomNavigationItem("Счет", R.drawable.account),
    BottomNavigationItem("Статьи", R.drawable.articles),
    BottomNavigationItem("Настройки", R.drawable.settings),
)

val tabRoutes = listOf("расходы", "доходы", "счет", "статьи", "настройки")
