package com.example.feature_expenses.ui.expenses.expenses_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.components.AddFloatingActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navigateToHistory: () -> Unit,
    navigateToAddTransaction: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Расходы") },
                actions = {
                    IconButton(onClick = navigateToHistory) {
                        Icon(
                            painter = painterResource(R.drawable.clocks),
                            contentDescription = "История расходов"
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
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Расходы")
        }
    }
} 