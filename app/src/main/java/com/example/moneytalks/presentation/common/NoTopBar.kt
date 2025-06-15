package com.example.moneytalks.presentation.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

object NoTopBar: AppBarProvider {
    @Composable
    override fun provideTopBar(navController: NavHostController) {}

}