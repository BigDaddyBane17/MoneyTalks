package com.example.moneytalks.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet

import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem
import com.example.moneytalks.presentation.common.TopAppBarState
import com.example.moneytalks.presentation.common.TopAppBarStateProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = viewModel(),
    navController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

//    TopAppBarStateProvider.update(
//        TopAppBarState(
//            title = "Мой счет",
//            trailingIcon = R.drawable.pen,
//            onTrailingIconClick = {
//                navController.navigate("счет_редактировать")
//            }
//        )
//    )

    LaunchedEffect(Unit) {
        viewModel.handleIntent(AccountIntent.LoadAccountData)

        viewModel.navigationEvent.collect { event ->
            when (event) {
                is AccountNavigationEvent.NavigateToBalance -> navController.navigate("счет_редактировать")
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFFF7F2FA)
        ) {
            Column {
                currencies.forEachIndexed { index, currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showSheet = false
                                viewModel.handleIntent(AccountIntent.CurrencyClick(
                                    if (currency.name == "Российский рубль") "₽"
                                    else if (currency.name == "Американский доллар") "$"
                                    else "€"
                                ))
                            }
                            .padding(horizontal = 16.dp)
                            .height(72.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = currency.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            text = currency.name,
                            color = Color.Black
                        )
                    }
                    if (index < currencies.size) {
                        HorizontalDivider()
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE46962))
                        .clickable { showSheet = false }
                        .padding(horizontal = 16.dp)
                        .height(72.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = Color.White
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "Отмена",
                        color = Color.White,
                    )
                }

            }
        }
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
                        showSheet = true
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


data class CurrencyItem(
    val name: String,
    val iconRes: Int
)

val currencies = listOf(
    CurrencyItem("Российский рубль", R.drawable.mdi_ruble),
    CurrencyItem("Американский доллар", R.drawable.mdi_dollar),
    CurrencyItem("Евро", R.drawable.mdi_euro),
)