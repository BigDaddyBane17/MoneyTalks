package com.example.moneytalks.features.account.presentation.create_account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun CreateAccount(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Text(
        text = "Создать счет"
    )
}