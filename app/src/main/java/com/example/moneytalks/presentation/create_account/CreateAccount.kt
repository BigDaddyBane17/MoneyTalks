package com.example.moneytalks.presentation.create_account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.TopAppBarState
import com.example.moneytalks.presentation.common.TopAppBarStateProvider

@Composable
fun CreateAccount(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Text(
        text = "Создать счет"
    )
}