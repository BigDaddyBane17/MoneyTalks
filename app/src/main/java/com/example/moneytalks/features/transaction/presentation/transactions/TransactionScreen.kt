package com.example.moneytalks.features.transaction.presentation.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.coreui.composable.ListItem
import com.example.moneytalks.navigation.Routes
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    accountId: Int?,
    type: String,
    viewModel: TransactionViewModel = hiltViewModel(),
    navigateToHistory: () -> Unit,
    navigateToAddTransaction: () -> Unit
) {
    val isIncome = type == "доходы"
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(accountId) {
        viewModel.handleIntent(
            TransactionIntent.LoadExpenses(
                accountId = accountId,
                startDate = LocalDate.now().toString(),
                endDate = LocalDate.now().toString()
            ),
            isIncome = isIncome
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (isIncome) "Доходы сегодня" else "Расходы сегодня",
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF27E780)
                ),
                actions = {
                    IconButton(onClick = navigateToHistory) {
                        Icon(
                            painter = painterResource(id = R.drawable.clocks),
                            contentDescription = "История",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 32.dp),
                onClick = navigateToAddTransaction,
                containerColor = Color(0xFF2AE881),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        },
        containerColor = Color(0xFFFef7ff)
    ) { innerPadding ->

        when (uiState) {
            is TransactionUiState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TransactionUiState.Success -> {
                val state = uiState as TransactionUiState.Success
                Column(
                    Modifier.padding(innerPadding)
                ) {
                    ListItem(
                        modifier = Modifier.height(56.dp),
                        title = "Всего",
                        currency = when (state.items.firstOrNull()?.account?.currency) {
                            "EUR" -> "€"
                            "USD" -> "$"
                            else -> "₽"
                        },
                        amount = state.total,
                        backgroundColor = Color(0xFFD4FAE6),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                    )
                    HorizontalDivider()

                    LazyColumn {
                        itemsIndexed(state.items) { _, item ->
                            ListItem(
                                title = item.category.name,
                                leadingIcon = item.category.emoji,
                                amount = item.amount,
                                description = item.comment,
                                trailingIcon = R.drawable.more_vert,
                                onClick = {
                                    viewModel.handleIntent(TransactionIntent.OnItemClicked, isIncome)
                                },
                                contentPadding = if (item.comment != null)
                                    PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                                else PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                                currency = when (state.items.firstOrNull()?.account?.currency) {
                                    "EUR" -> "€"
                                    "USD" -> "$"
                                    else -> "₽"
                                },
                                modifier = Modifier
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }

            is TransactionUiState.Error -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as TransactionUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}
