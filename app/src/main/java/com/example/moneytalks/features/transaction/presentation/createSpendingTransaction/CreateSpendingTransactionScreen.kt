package com.example.moneytalks.features.transaction.presentation.createSpendingTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.coreui.composable.ListItem
import com.example.moneytalks.features.transaction.presentation.createSpendingTransaction.CreateSpendingTransactionIntent.*
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSpendingTransactionScreen(
    navController: NavHostController,
    viewModel: CreateSpendingTransactionViewModel = hiltViewModel(),
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showAccountMenu by remember { mutableStateOf(false) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    var showAmountDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }

    var amountText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Мои расходы") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.handleIntent(SubmitTransaction)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ok),
                            contentDescription = "Добавить транзакцию",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF27E780)
                ),
            )
        },
        containerColor = Color(0xFFFef7ff)
    ) { innerPadding ->

        when (val state = uiState) {
            is CreateSpendingTransactionUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CreateSpendingTransactionUiState.Data -> {
                Column(
                    Modifier.padding(innerPadding)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = showAccountMenu,
                        onExpandedChange = { showAccountMenu = !showAccountMenu }
                    ) {
                        ListItem(
                            modifier = Modifier.menuAnchor((MenuAnchorType.PrimaryEditable)),
                            title = "Счет",
                            amount = viewModel.selectedAccount?.name ?: "Выберите счет",
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
                                    text = { Text(account.name) },
                                    onClick = {
                                        viewModel.handleIntent(SetAccount(account.id))
                                        showAccountMenu = false
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    ExposedDropdownMenuBox(
                        expanded = showCategoryMenu,
                        onExpandedChange = { showCategoryMenu = !showCategoryMenu }
                    ) {
                        ListItem(
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable),
                            title = "Статья",
                            amount = viewModel.selectedCategory?.name ?: "Выберите статью",
                            trailingIcon = R.drawable.more_vert,
                            onClick = { showCategoryMenu = true }
                        )
                        ExposedDropdownMenu(
                            expanded = showCategoryMenu,
                            onDismissRequest = { showCategoryMenu = false }
                        ) {
                            state.categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.name) },
                                    onClick = {
                                        viewModel.handleIntent(SetCategory(category.id))
                                        showCategoryMenu = false
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Сумма",
                        amount = viewModel.amount.ifBlank { "Введите сумму" },
                        currency = "₽",
                        trailingIcon = R.drawable.more_vert,
                        onClick = {
                            amountText = viewModel.amount
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
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.handleIntent(SetAmount(amountText))
                                    showAmountDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showAmountDialog = false }) { Text("Отмена") }
                            }
                        )
                    }

                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Дата",
                        amount = viewModel.date.ifBlank { "Выберите дату" },
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
                                        viewModel.handleIntent(SetDate(localDate.toString()))
                                    }
                                    showDatePicker = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showDatePicker = false }) { Text("Отмена") }
                            }
                        ) { DatePicker(state = datePickerState) }
                    }

                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Время",
                        amount = viewModel.time.ifBlank { "Выберите время" },
                        trailingIcon = R.drawable.more_vert,
                        onClick = { showTimePicker = true }
                    )
                    if (showTimePicker) {
                        Dialog(onDismissRequest = { showTimePicker = false }) {
                            Surface(modifier = Modifier.padding(24.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Выбрать время")
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TimePicker(state = timePickerState)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(onClick = { showTimePicker = false }) { Text("Отмена") }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        TextButton(onClick = {
                                            val hour = timePickerState.hour
                                            val minute = timePickerState.minute
                                            val timeStr = "%02d:%02d".format(hour, minute)
                                            viewModel.handleIntent(SetTime(timeStr))
                                            showTimePicker = false
                                        }) { Text("Ок") }
                                    }
                                }
                            }
                        }
                    }

                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Комментарий",
                        amount = viewModel.comment.ifBlank { "Комментарий" },
                        trailingIcon = R.drawable.more_vert,
                        onClick = {
                            commentText = viewModel.comment
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
                                    viewModel.handleIntent(SetComment(commentText))
                                    showCommentDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showCommentDialog = false }) { Text("Отмена") }
                            }
                        )
                    }
                }
            }

            is CreateSpendingTransactionUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.reset()
                        }) {
                            Text("Назад")
                        }
                    }
                }
            }

            is CreateSpendingTransactionUiState.Success -> {
                navController.popBackStack()
                viewModel.reset()
            }

        }

    }
}
