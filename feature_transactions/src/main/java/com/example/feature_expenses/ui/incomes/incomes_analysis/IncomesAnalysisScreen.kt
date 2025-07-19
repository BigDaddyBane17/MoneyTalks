package com.example.feature_expenses.ui.incomes.incomes_analysis

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import com.example.core_ui.components.CustomDatePickerDialog
import com.example.core_ui.components.ListItem
import com.example.core.utils.toCurrencySymbol
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomesAnalysisScreen(
    navigateBack: () -> Unit,
    viewModel: IncomesAnalysisViewModel
) {
    var startDate by rememberSaveable { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))

    LaunchedEffect(startDate, endDate) {
        viewModel.handleIntent(
            IncomesAnalysisIntent.LoadHistory(
                startDate = startDate,
                endDate = endDate
            )
        )
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State    .RESUMED) {
            viewModel.handleIntent(IncomesAnalysisIntent.Refresh)
        }
    }

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val currencySymbol = uiState.currency.toCurrencySymbol()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Анализы")
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

            when {
                uiState.isLoading -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text(
                        uiState.error!!,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
                else -> {
                    ListItem(
                        title = "Сумма",
                        amount = uiState.totalAmount,
                        currency = currencySymbol,
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                    )

                    if (uiState.expenses.isEmpty()) {
                        Text(
                            "Нет операций",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", Locale("ru"))
                        uiState.expenses
                            .sortedBy { it.transactionDate }
                            .forEach { transaction ->
                                val percent = transaction.amount.toDouble() / uiState.totalAmount.toDouble() * 100
                                val percentStr = when {
                                    percent < 0.01 -> "<0.01%"
                                    else -> String.format(Locale.getDefault(), "%.2f%%", percent)
                                }
                                ListItem(
                                    title = transaction.categoryName,
                                    leadingIcon = transaction.categoryEmoji,
                                    trailingIcon = R.drawable.more_vert,
                                    amount = percentStr,
                                    description = transaction.comment,
                                    subtitle = "${transaction.amount} $currencySymbol",
                                    modifier = Modifier,
                                    onClick = {}
                                )
                                HorizontalDivider()
                            }
                    }
                }
            }
        }

        if (showDialog) {
            val initialDate = when (pickerTarget) {
                "start" -> startDate
                "end" -> endDate
                else -> LocalDate.now()
            }
            
            CustomDatePickerDialog(
                onDateSelected = { selectedDate ->
                    when (pickerTarget) {
                        "start" -> startDate = selectedDate
                        "end" -> endDate = selectedDate
                    }
                },
                onDismiss = { showDialog = false },
                initialDate = initialDate
            )
        }
    }
}