package com.example.feature_account.ui.account_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_ui.R
import com.example.core_ui.components.ListItem
import com.example.core.utils.toCurrencySymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountEditScreen(
    onBack: () -> Unit,
    viewModel: AccountEditViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    
    var showNameDialog by remember { mutableStateOf(false) }
    var showBalanceDialog by remember { mutableStateOf(false) }
    var nameText by remember { mutableStateOf("") }
    var balanceText by remember { mutableStateOf("") }

    LaunchedEffect(state.name) {
        nameText = state.name
    }
    
    LaunchedEffect(state.balance) {
        balanceText = state.balance
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Редактировать счет") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.handleIntent(AccountEditIntent.SaveAccount) },
                        enabled = state.isFormValid && !state.isSaving
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "Сохранить"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.error!!)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.handleIntent(AccountEditIntent.ClearError)
                        }) {
                            Text("Повторить")
                        }
                    }
                }
            }

            else -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    // Название счета
                    ListItem(
                        title = "Название счета",
                        amount = state.name.ifBlank { "Введите название" },
                        trailingIcon = R.drawable.more_vert,
                        onClick = {
                            nameText = state.name
                            showNameDialog = true
                        },
                        modifier = Modifier
                    )
                    if (showNameDialog) {
                        AlertDialog(
                            onDismissRequest = { showNameDialog = false },
                            title = { Text("Название счета") },
                            text = {
                                TextField(
                                    value = nameText,
                                    onValueChange = { nameText = it },
                                    label = { Text("Название") },
                                    singleLine = true,
                                    isError = state.nameError != null,
                                    supportingText = state.nameError?.let { { Text(it) } }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.handleIntent(AccountEditIntent.NameChanged(nameText))
                                    showNameDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showNameDialog = false }) { Text("Отмена") }
                            }
                        )
                    }

                    HorizontalDivider()

                    // Баланс
                    ListItem(
                        title = "Баланс",
                        amount = if (state.balance.isNotBlank()) {
                            "${state.balance} ${state.currency.toCurrencySymbol()}"
                        } else {
                            "Введите баланс"
                        },
                        trailingIcon = R.drawable.more_vert,
                        onClick = {
                            balanceText = state.balance
                            showBalanceDialog = true
                        },
                        modifier = Modifier
                    )
                    if (showBalanceDialog) {
                        AlertDialog(
                            onDismissRequest = { showBalanceDialog = false },
                            title = { Text("Баланс") },
                            text = {
                                TextField(
                                    value = balanceText,
                                    onValueChange = { balanceText = it },
                                    label = { Text("Баланс") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                                    isError = state.balanceError != null,
                                    supportingText = state.balanceError?.let { { Text(it) } }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.handleIntent(AccountEditIntent.BalanceChanged(balanceText))
                                    showBalanceDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showBalanceDialog = false }) { Text("Отмена") }
                            }
                        )
                    }

                    HorizontalDivider()

                    // Информация о валюте (только чтение)
                    ListItem(
                        title = "Валюта",
                        amount = state.currency.toCurrencySymbol(),
                        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}