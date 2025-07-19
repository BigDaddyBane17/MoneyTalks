package com.example.feature_account.ui.account_main

import android.util.Log
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.core_ui.R
import com.example.core_ui.components.ListItem
import com.example.feature_account.models.CurrencyProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navigateToAccountEdit: () -> Unit,
    viewModel: AccountViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var showAccountMenu by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val currencies = CurrencyProvider.supportedCurrencies
    val lifecycleOwner = LocalLifecycleOwner.current

//    LaunchedEffect(lifecycleOwner) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//            viewModel.handleIntent(AccountIntent.Refresh)
//        }
//    }

    val accounts = when (val state = uiState) {
        is AccountUiState.Success -> state.accounts
        else -> emptyList()
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column {
                currencies.forEachIndexed { index, currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showSheet = false
                                viewModel.handleIntent(AccountIntent.CurrencyClick(currency.code))
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
                    if (index < currencies.size - 1) {
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

    Scaffold(
        topBar = {
            Box {
                CenterAlignedTopAppBar(
                    title = { Text("Мои счета") },
                    navigationIcon = {
                        IconButton(onClick = {
                            showAccountMenu = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.choose_account),
                                contentDescription = "Выбор аккаунта"
                            )
                        }
                    },
                    actions = {
                        // Убираем кнопку редактирования
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                )
                DropdownMenu(
                    expanded = showAccountMenu,
                    onDismissRequest = { showAccountMenu = false }
                ) {
                    accounts.forEach { account ->
                        Log.d("accs", account.name)
                        DropdownMenuItem(
                            text = { Text(account.name) },
                            onClick = {
                                viewModel.selectAccount(account.id)
                                showAccountMenu = false
                            }
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val state = uiState) {
            is AccountUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AccountUiState.Success -> {
                val currencySymbol = currencies.firstOrNull { it.code == state.account?.currency }?.symbol
                    ?: state.account?.currency.orEmpty()

                Column(
                    Modifier.padding(innerPadding)
                ) {
                    ListItem(
                        title = "Баланс",
                        amount = state.account?.balance,
                        currency = currencySymbol,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                        leadingIcon = "\uD83D\uDCB0",
                        trailingIcon = R.drawable.more_vert,
                        onClick = navigateToAccountEdit,
                        modifier = Modifier.height(56.dp)
                    )
                    HorizontalDivider()
                    ListItem(
                        title = "Валюта",
                        amount = currencySymbol,
                        backgroundColor = MaterialTheme.colorScheme.surface,
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
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}