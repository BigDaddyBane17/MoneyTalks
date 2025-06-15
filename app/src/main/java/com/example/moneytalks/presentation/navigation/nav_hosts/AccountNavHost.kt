package com.example.moneytalks.presentation.navigation.nav_hosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytalks.presentation.account.AccountScreen

@Composable
fun AccountNavHost() {
    val accountNavController = rememberNavController()
    NavHost(accountNavController, startDestination = "счет") {
        composable("счет") {
        }
    }
}

