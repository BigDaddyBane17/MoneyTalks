package com.example.feature_settings.ui.pinentry

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.prefs.SettingsPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinEntryScreen(
    onPinCorrect: () -> Unit,
    settingsPreferences: SettingsPreferences
) {
    var pinCode by remember { mutableStateOf("") }
    var storedPinCode by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Получаем сохраненный PIN-код
    LaunchedEffect(Unit) {
        settingsPreferences.pinCode.collect { pin ->
            storedPinCode = pin
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Заголовок
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Введите PIN-код",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Для входа в приложение",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Индикаторы PIN-кода
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = if (index < pinCode.length) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outline
                                },
                                shape = CircleShape
                            )
                            .background(
                                if (index < pinCode.length) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    Color.Transparent
                                }
                            )
                    )
                }
            }

            // Сообщение об ошибке
            if (showError) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Цифровая клавиатура
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                items((1..9).toList()) { number ->
                    NumberButton(
                        number = number.toString(),
                        onClick = {
                            if (pinCode.length < 4) {
                                pinCode += number.toString()
                                showError = false
                                
                                // Проверяем PIN-код при вводе 4 цифр
                                if (pinCode.length == 4) {
                                    if (pinCode == storedPinCode) {
                                        onPinCorrect()
                                    } else {
                                        showError = true
                                        errorMessage = "Неверный PIN-код"
                                        pinCode = ""
                                    }
                                }
                            }
                        }
                    )
                }
                
                // Пустая кнопка
                item {
                    Box(modifier = Modifier.size(64.dp))
                }
                
                // Кнопка 0
                item {
                    NumberButton(
                        number = "0",
                        onClick = {
                            if (pinCode.length < 4) {
                                pinCode += "0"
                                showError = false
                                
                                if (pinCode.length == 4) {
                                    if (pinCode == storedPinCode) {
                                        onPinCorrect()
                                    } else {
                                        showError = true
                                        errorMessage = "Неверный PIN-код"
                                        pinCode = ""
                                    }
                                }
                            }
                        }
                    )
                }
                
                // Кнопка удаления
                item {
                    IconButton(
                        onClick = {
                            if (pinCode.isNotEmpty()) {
                                pinCode = pinCode.dropLast(1)
                                showError = false
                            }
                        },
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Удалить",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 