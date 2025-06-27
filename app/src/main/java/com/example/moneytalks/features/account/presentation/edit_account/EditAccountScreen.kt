package com.example.moneytalks.features.account.presentation.edit_account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.coreui.TopAppBarState
import com.example.moneytalks.coreui.TopAppBarStateProvider

@Composable
fun EditAccountScreen(
    navController: NavHostController
) {

    TopAppBarStateProvider.update(
        TopAppBarState(
            title = "Мой счет",
            trailingIcon = R.drawable.ok,
            leadingIcon = R.drawable.cancel,
            onTrailingIconClick = {

            },
            onLeadingIconClick = {
                navController.popBackStack()
            }

        )
    )

    Text(
        text = "Редактирование счета"
    )
}