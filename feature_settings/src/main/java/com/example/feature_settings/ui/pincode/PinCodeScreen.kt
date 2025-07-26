package com.example.feature_settings.ui.pincode

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature_settings.ui.getSettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: com.example.feature_settings.ui.SettingsViewModel = viewModel(
        factory = getSettingsViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var pinCode by remember { mutableStateOf("") }
    var confirmPinCode by remember { mutableStateOf("") }
    var isSettingPin by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PIN-код") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (uiState.pinCode != null) Icons.Default.Lock else Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (uiState.pinCode != null) "Изменить PIN-код" else "Установить PIN-код",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Введите 4-значный PIN-код для защиты приложения",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            if (showError) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
            }

            if (uiState.pinCode == null || isSettingPin) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = if (isSettingPin) "Подтвердите PIN-код" else "Введите PIN-код",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            OutlinedTextField(
                                value = if (isSettingPin) confirmPinCode else pinCode,
                                onValueChange = { value ->
                                    if (value.length <= 4) {
                                        if (isSettingPin) {
                                            confirmPinCode = value
                                        } else {
                                            pinCode = value
                                        }
                                        showError = false
                                    }
                                },
                                label = { Text("PIN-код") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        pinCode = ""
                                        confirmPinCode = ""
                                        isSettingPin = false
                                        showError = false
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Отмена")
                                }
                                
                                Button(
                                    onClick = {
                                        if (isSettingPin) {
                                            if (confirmPinCode == pinCode) {
                                                viewModel.updatePinCode(pinCode)
                                                onNavigateBack()
                                            } else {
                                                showError = true
                                                errorMessage = "PIN-коды не совпадают"
                                            }
                                        } else {
                                            if (pinCode.length == 4) {
                                                isSettingPin = true
                                            } else {
                                                showError = true
                                                errorMessage = "PIN-код должен содержать 4 цифры"
                                            }
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(if (isSettingPin) "Подтвердить" else "Далее")
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "PIN-код установлен",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        viewModel.updatePinCode(null)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Удалить PIN-код")
                                }
                                
                                Button(
                                    onClick = {
                                        isSettingPin = true
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Изменить")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 