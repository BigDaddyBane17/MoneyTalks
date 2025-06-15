package com.example.moneytalks.presentation.common

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController


interface AppBarProvider {
    @Composable
    fun provideTopBar(
        navController: NavHostController
    )
}