package com.example.moneytalks.presentation.item_expenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneytalks.presentation.common.ListItem
import com.example.moneytalks.presentation.common.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemExpensesScreen(
    viewModel: ItemExpensesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ItemExpensesIntent.LoadItemExpenses)
    }



    when (uiState) {
        is ItemExpenseUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ItemExpenseUiState.Success -> {
            val expenses = (uiState as ItemExpenseUiState.Success).items

            Column {
                SearchBar(
                    onSearch = { query ->
                        searchQuery = query
                        viewModel.handleIntent(ItemExpensesIntent.SearchItemExpenses(query))
                    }
                )

                LazyColumn {
                    itemsIndexed(expenses) { _, item ->
                        ListItem(
                            title = item.title,
                            leadingIcon = item.leadIcon,
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            ),
                            modifier = Modifier
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        is ItemExpenseUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = (uiState as ItemExpenseUiState.Error).message,
                    color = Color.Red
                )
            }
        }
    }
}


