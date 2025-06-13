package com.example.moneytalks.presentation.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem

@Composable
fun AccountScreen(viewModel: AccountViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(AccountIntent.LoadAccountData)
    }


    when (uiState) {
        is AccountUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AccountUiState.Success -> {
            val state = uiState as AccountUiState.Success
            Column {
                ListItem(
                    title = "Баланс",
                    amount = state.account.amount,
                    backgroundColor = Color(0xFFD4FAE6),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    trailingIcon = R.drawable.more_vert,
                    onClick = {
                        viewModel.handleIntent(AccountIntent.BalanceClick)
                    },
                    leadingIcon = "\uD83D\uDCB0",
                    modifier = Modifier.height(56.dp)
                )
                HorizontalDivider()
                ListItem(
                    title = "Валюта",
                    amount = state.account.currency,
                    backgroundColor = Color(0xFFD4FAE6),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    trailingIcon = R.drawable.more_vert,
                    onClick = {
                        viewModel.handleIntent(AccountIntent.CurrencyClick)
                    },
                    modifier = Modifier.height(56.dp)
                )
            }
        }

        is AccountUiState.Error -> {
            Text(
                text = (uiState as AccountUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}
