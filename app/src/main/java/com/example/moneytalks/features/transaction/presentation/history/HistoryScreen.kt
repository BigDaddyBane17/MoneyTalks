package com.example.moneytalks.features.transaction.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.moneytalks.features.account.presentation.account.AccountViewModel
import com.example.moneytalks.navigation.Routes
import java.time.Instant
import java.time.LocalDate.now
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    type: String,
    accountId: Int?,
    viewModel: HistoryViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel,
    navigateToAnalysis: () -> Unit,
    navigateBack: () -> Unit
) {
    val isIncome = type == "доходы"

    val selectedAccount by accountViewModel.selectedAccount.collectAsStateWithLifecycle()

    val currencySymbol = when (selectedAccount?.currency) {
        "EUR" -> "€"
        "USD" -> "$"
        "RUB" -> "₽"
        else -> ""
    }

    // Даты фильтрации
    var startDate by rememberSaveable { mutableStateOf(now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))

    LaunchedEffect(accountId, startDate, endDate, type) {
        viewModel.handleIntent(
            HistoryIntent.LoadHistory(
                accountId = accountId,
                startDate = startDate.toString(),
                endDate = endDate.toString()
            ),
            isIncome = isIncome
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (isIncome) "История доходов" else "История расходов",
                    )
                },
                actions = {
                    IconButton(
                        onClick = navigateToAnalysis
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.history), // хз я чет назвал это не так, на самом деле тут анализы...
                            contentDescription = "Анализ",
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // Блок выбора дат
            ListItem(
                title = "Начало",
                amount = startDate.format(dateFormatter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier,
                onClick = {
                    pickerTarget = "start"
                    showDialog = true
                }
            )
            HorizontalDivider()
            ListItem(
                title = "Конец",
                amount = endDate.format(dateFormatter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier,
                onClick = {
                    pickerTarget = "end"
                    showDialog = true
                }
            )
            HorizontalDivider()

            when (val state = uiState) {
                is HistoryUiState.Loading -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is HistoryUiState.Success -> {
                    ListItem(
                        title = "Сумма",
                        amount = state.total,
                        currency = currencySymbol,
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                    )

                    if (state.items.isEmpty()) {
                        Text(
                            "Нет операций",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", Locale("ru"))
                        state.items
                            .sortedBy {
                                Instant.parse(it.transactionDate)
                            }
                            .forEach { transaction ->
                                val formattedDate = try {
                                    val instant = Instant.parse(transaction.transactionDate)
                                    val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                                    dateTime.format(outputFormatter)
                                } catch (e: Exception) {
                                    transaction.transactionDate
                                }
                                ListItem(
                                    title = transaction.category.name,
                                    leadingIcon = transaction.category.emoji,
                                    trailingIcon = R.drawable.more_vert,
                                    amount = transaction.amount,
                                    currency = currencySymbol,
                                    description = transaction.comment,
                                    subtitle = formattedDate,
                                    modifier = Modifier,
                                    onClick = {}
                                )
                                HorizontalDivider()
                            }
                    }
                }
                is HistoryUiState.Error -> {
                    Text(
                        state.message,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
            }
        }

        if (showDialog) {
            val initialDateMillis = when (pickerTarget) {
                "start" -> startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                "end" -> endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                else -> now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val picked = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                            when (pickerTarget) {
                                "start" -> startDate = picked
                                "end" -> endDate = picked
                            }
                        }
                        showDialog = false
                    }) {
                        Text("Ок")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Отмена") }
                }
            ) {
                DatePicker(state = datePickerState, showModeToggle = false)
            }
        }
    }
}
