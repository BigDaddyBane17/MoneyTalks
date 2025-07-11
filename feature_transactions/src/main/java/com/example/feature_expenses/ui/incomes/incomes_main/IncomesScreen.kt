package com.example.feature_expenses.ui.incomes.incomes_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_ui.R
import com.example.core_ui.components.AddFloatingActionButton
import com.example.core_ui.components.ListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomesScreen(
    navigateToHistory: () -> Unit,
    navigateToAddTransaction: () -> Unit,
    navigateToEditTransaction: (Int) -> Unit,
    viewModel: IncomesViewModel
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.handleIntent(IncomesIntent.Refresh)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Доходы за сегодня") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleIntent(IncomesIntent.Refresh) }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Обновить"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = navigateToHistory) {
                        Icon(
                            painter = painterResource(R.drawable.clocks),
                            contentDescription = "История доходов"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        floatingActionButton = {
            AddFloatingActionButton(
                onClick = navigateToAddTransaction
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        IncomesContent(
            state = state,
            onTransactionClick = navigateToEditTransaction,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun IncomesContent(
    state: IncomesState,
    onTransactionClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading && state.incomes.isEmpty() -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        state.error != null -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        state.incomes.isEmpty() -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Сегодня доходов пока нет",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        else -> {
            Column(modifier = modifier) {
                ListItem(
                    modifier = Modifier.height(56.dp),
                    title = "Всего",
                    currency = state.currency,
                    amount = state.totalAmount,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                )
                HorizontalDivider()

                LazyColumn {
                    itemsIndexed(state.incomes) { _, income ->
                        ListItem(
                            title = income.categoryName,
                            leadingIcon = income.categoryEmoji,
                            amount = income.amount,
                            description = income.comment,
                            trailingIcon = R.drawable.more_vert,
                            onClick = {
                                onTransactionClick(income.id)
                            },
                            contentPadding = if (income.comment != null)
                                PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                            else PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                            currency = state.currency,
                            modifier = Modifier
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}