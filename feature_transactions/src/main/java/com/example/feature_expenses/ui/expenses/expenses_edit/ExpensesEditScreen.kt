package com.example.feature_expenses.ui.expenses.expenses_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_ui.R
import com.example.core_ui.components.ListItem
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesEditScreen(
    onBack: () -> Unit,
    viewModel: ExpensesEditViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    var showAccountMenu by remember { mutableStateOf(false) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    var showAmountDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    var amountText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    LaunchedEffect(state.amount) {
        amountText = state.amount
    }
    
    LaunchedEffect(state.comment) {
        commentText = state.comment
    }

    LaunchedEffect(state.isUpdated, state.isDeleted) {
        if (state.isUpdated || state.isDeleted) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Редактировать расход") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    // Only save button
                    IconButton(
                        onClick = {
                            viewModel.handleIntent(ExpensesEditIntent.UpdateTransaction)
                        },
                        enabled = state.isFormValid && !state.isUpdating && !state.isDeleting
                    ) {
                        if (state.isUpdating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = "Сохранить изменения"
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
    ) { innerPadding ->

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
                            viewModel.handleIntent(ExpensesEditIntent.ClearError)
                        }) {
                            Text("Повторить")
                        }
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    // Form content
                    Column(modifier = Modifier.weight(1f)) {
                        
                        // Account Selection
                        ExposedDropdownMenuBox(
                            expanded = showAccountMenu,
                            onExpandedChange = { showAccountMenu = !showAccountMenu }
                        ) {
                            ListItem(
                                modifier = Modifier.menuAnchor(),
                                title = "Счет",
                                amount = state.selectedAccount?.name ?: "Выберите счет",
                                trailingIcon = R.drawable.more_vert,
                                onClick = { showAccountMenu = true }
                            )
                            HorizontalDivider()
                            ExposedDropdownMenu(
                                expanded = showAccountMenu,
                                onDismissRequest = { showAccountMenu = false }
                            ) {
                                state.accounts.forEach { account ->
                                    DropdownMenuItem(
                                        text = { 
                                            Column {
                                                Text(account.name)
                                                Text(
                                                    "${account.balance} ${account.currency}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                                )
                                            }
                                        },
                                        onClick = {
                                            viewModel.handleIntent(ExpensesEditIntent.SelectAccount(account))
                                            showAccountMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        HorizontalDivider()

                        // Category Selection
                        ExposedDropdownMenuBox(
                            expanded = showCategoryMenu,
                            onExpandedChange = { showCategoryMenu = !showCategoryMenu }
                        ) {
                            ListItem(
                                modifier = Modifier.menuAnchor(),
                                title = "Статья",
                                amount = state.selectedCategory?.let { "${it.emoji} ${it.name}" } ?: "Выберите статью",
                                trailingIcon = R.drawable.more_vert,
                                onClick = { showCategoryMenu = true }
                            )
                            ExposedDropdownMenu(
                                expanded = showCategoryMenu,
                                onDismissRequest = { showCategoryMenu = false }
                            ) {
                                state.categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(category.emoji, style = MaterialTheme.typography.titleMedium)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(category.name)
                                            }
                                        },
                                        onClick = {
                                            viewModel.handleIntent(ExpensesEditIntent.SelectCategory(category))
                                            showCategoryMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        HorizontalDivider()
                        
                        // Amount Input
                        ListItem(
                            modifier = Modifier,
                            title = "Сумма",
                            amount = if (state.amount.isNotBlank()) {
                                "${state.amount} ${state.selectedAccount?.currency ?: ""}"
                            } else {
                                "Введите сумму"
                            },
                            trailingIcon = R.drawable.more_vert,
                            onClick = {
                                amountText = state.amount
                                showAmountDialog = true
                            }
                        )
                        if (showAmountDialog) {
                            AlertDialog(
                                onDismissRequest = { showAmountDialog = false },
                                title = { Text("Введите сумму") },
                                text = {
                                    TextField(
                                        value = amountText,
                                        onValueChange = { amountText = it },
                                        label = { Text("Сумма") },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                                        isError = state.amountError != null,
                                        supportingText = state.amountError?.let { { Text(it) } }
                                    )
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        viewModel.handleIntent(ExpensesEditIntent.AmountChanged(amountText))
                                        showAmountDialog = false
                                    }) { Text("Ок") }
                                },
                                dismissButton = {
                                    Button(onClick = { showAmountDialog = false }) { Text("Отмена") }
                                }
                            )
                        }

                        HorizontalDivider()
                        
                        // Date Selection
                        ListItem(
                            modifier = Modifier,
                            title = "Дата",
                            amount = state.selectedDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            trailingIcon = R.drawable.more_vert,
                            onClick = { showDatePicker = true }
                        )
                        if (showDatePicker) {
                            DatePickerDialog(
                                onDismissRequest = { showDatePicker = false },
                                confirmButton = {
                                    Button(onClick = {
                                        datePickerState.selectedDateMillis?.let { ms ->
                                            val localDate = Instant.ofEpochMilli(ms)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                            viewModel.handleIntent(ExpensesEditIntent.DateSelected(localDate))
                                        }
                                        showDatePicker = false
                                    }) { Text("Ок") }
                                },
                                dismissButton = {
                                    Button(onClick = { showDatePicker = false }) { Text("Отмена") }
                                }
                            ) { 
                                DatePicker(state = datePickerState) 
                            }
                        }

                        HorizontalDivider()
                        
                        // Time Selection
                        ListItem(
                            modifier = Modifier,
                            title = "Время",
                            amount = state.selectedDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                            trailingIcon = R.drawable.more_vert,
                            onClick = { showTimePicker = true }
                        )
                        if (showTimePicker) {
                            Dialog(onDismissRequest = { showTimePicker = false }) {
                                Surface(
                                    modifier = Modifier.padding(24.dp),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Выбрать время")
                                        Spacer(modifier = Modifier.height(12.dp))
                                        TimePicker(state = timePickerState)
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            TextButton(onClick = { showTimePicker = false }) { 
                                                Text("Отмена") 
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            TextButton(onClick = {
                                                val hour = timePickerState.hour
                                                val minute = timePickerState.minute
                                                val time = java.time.LocalTime.of(hour, minute)
                                                viewModel.handleIntent(ExpensesEditIntent.TimeSelected(time))
                                                showTimePicker = false
                                            }) { 
                                                Text("Ок") 
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        HorizontalDivider()
                        
                        // Comment Input
                        ListItem(
                            modifier = Modifier,
                            title = "Комментарий",
                            amount = state.comment.ifBlank { "Комментарий" },
                            trailingIcon = R.drawable.more_vert,
                            onClick = {
                                commentText = state.comment
                                showCommentDialog = true
                            }
                        )
                        HorizontalDivider()
                        if (showCommentDialog) {
                            AlertDialog(
                                onDismissRequest = { showCommentDialog = false },
                                title = { Text("Комментарий") },
                                text = {
                                    TextField(
                                        value = commentText,
                                        onValueChange = { commentText = it },
                                        label = { Text("Комментарий") }
                                    )
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        viewModel.handleIntent(ExpensesEditIntent.CommentChanged(commentText))
                                        showCommentDialog = false
                                    }) { Text("Ок") }
                                },
                                dismissButton = {
                                    Button(onClick = { showCommentDialog = false }) { Text("Отмена") }
                                }
                            )
                        }
                    }
                    
                    // Delete button at bottom
                    Button(
                        onClick = { showDeleteConfirmDialog = true },
                        enabled = !state.isUpdating && !state.isDeleting,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        if (state.isDeleting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onError
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Удаление...")
                        } else {
                            Text("Удалить расход")
                        }
                    }
                }
            }
        }

        if (showDeleteConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmDialog = false },
                title = { Text("Удалить расход") },
                text = { Text("Вы уверены, что хотите удалить эту транзакцию?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.handleIntent(ExpensesEditIntent.DeleteTransaction)
                            showDeleteConfirmDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) { Text("Удалить") }
                },
                dismissButton = {
                    Button(onClick = { showDeleteConfirmDialog = false }) { Text("Отмена") }
                }
            )
        }
    }
} 