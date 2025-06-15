package com.example.moneytalks.presentation.earnings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem

@Composable
fun EarningsScreen(
    viewModel: EarningsViewModel = viewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(EarningsIntent.LoadEarnings)
    }

    Column {
        when (uiState) {
            is EarningsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is EarningsUiState.Success -> {
                val state = uiState as EarningsUiState.Success
                val sum = state.items.sumOf { it.amount.replace(" ", "").toInt() }
                val totalAmount = "%,d".format(sum)
                    .replace(",", " ") + " ₽"

                ListItem(
                    title = "Всего",
                    amount = totalAmount,
                    backgroundColor = Color(0xFFD4FAE6),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    modifier = Modifier.height(56.dp)
                )
                HorizontalDivider()

                LazyColumn {
                    itemsIndexed(state.items) { _, item ->
                        ListItem(
                            title = item.title,
                            amount = "${item.amount} ${item.currency}",
                            trailingIcon = R.drawable.more_vert,
                            onClick = { viewModel.handleIntent(EarningsIntent.OnItemClicked) },
                            modifier = Modifier
                        )
                        HorizontalDivider()
                    }
                }
            }

            is EarningsUiState.Error -> {
                Text(
                    text = (uiState as EarningsUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}