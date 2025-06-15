package com.example.moneytalks.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.moneytalks.presentation.common.ListItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    type: String
) {
    var startDate by rememberSaveable { mutableStateOf(now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))

    Column {
        ListItem(
            title = "Начало",
            amount = startDate.format(dateFormatter),
            modifier = Modifier,
            backgroundColor = Color(0xFFD4FAE6),
            onClick = {
                pickerTarget = "start"
                showDialog = true
            }
        )
        HorizontalDivider()
        ListItem(
            title = "Конец",
            amount = endDate.format(dateFormatter),
            modifier = Modifier,
            backgroundColor = Color(0xFFD4FAE6),
            onClick = {
                pickerTarget = "end"
                showDialog = true
            }
        )
        HorizontalDivider()
        ListItem(
            title = "Сумма",
            amount = "125 868",
            currency = "₽",
            modifier = Modifier,
            backgroundColor = Color(0xFFD4FAE6),
        )
    }

    if (showDialog) {
        var datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = when (pickerTarget) {
                "start" -> startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                "end" -> endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                else -> now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
        )

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
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}