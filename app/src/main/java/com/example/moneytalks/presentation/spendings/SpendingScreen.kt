package com.example.moneytalks.presentation.spendings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun SpendingScreen(viewModel: SpendingViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(SpendingIntent.LoadExpenses)
    }

    when (uiState) {
        is SpendingUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is SpendingUiState.Success -> {
            val state = uiState as SpendingUiState.Success

            Column {
                ListItem(
                    title = "Всего",
                    amount = state.total,
                    backgroundColor = Color(0xFFD4FAE6),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                )
                HorizontalDivider()

                LazyColumn {
                    itemsIndexed(state.items) { _, item ->
                        ListItem(
                            title = item.title,
                            leadingIcon = item.leadIcon,
                            amount = item.amount,
                            description = item.description,
                            trailingIcon = R.drawable.more_vert,
                            onClick = {
                                viewModel.handleIntent(SpendingIntent.OnItemClicked)
                            },
                            contentPadding = if (item.description != null)
                                PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                            else PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                            currency = item.currency
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        is SpendingUiState.Error -> {
            Text(
                text = (uiState as SpendingUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}

