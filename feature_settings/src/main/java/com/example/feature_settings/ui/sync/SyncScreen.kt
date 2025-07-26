package com.example.feature_settings.ui.sync

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_ui.theme.ThemeProvider
import com.example.feature_settings.models.SyncFrequency
import com.example.feature_settings.ui.getSettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: com.example.feature_settings.ui.SettingsViewModel = viewModel(
        factory = getSettingsViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    val context = LocalContext.current
    val appComponent = (context.applicationContext as com.example.core.di.FeatureComponentProvider).provideFeatureComponent()
    val settingsPreferences = appComponent.settingsPreferences()
    val appThemeId by settingsPreferences.appThemeId.collectAsState(initial = "default")
    val selectedTheme = ThemeProvider.getThemeById(appThemeId)
    
    // Инициализируем слайдер значением из настроек
    var sliderValue by remember { mutableStateOf(24f) }
    
    // Обновляем слайдер при изменении настроек
    LaunchedEffect(uiState.syncFrequencyHours) {
        sliderValue = uiState.syncFrequencyHours.toFloat()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Синхронизация") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = selectedTheme.primaryColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Частота синхронизации",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Выберите, как часто приложение будет синхронизировать данные с сервером",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

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
                            text = "Настройка времени",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Текущее время синхронизации: ${formatHours(sliderValue.toInt())}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Slider(
                            value = sliderValue,
                            onValueChange = { newValue ->
                                sliderValue = newValue
                                // Обновляем настройки при изменении слайдера
                                val hours = newValue.toInt()
                                if (hours > 0) {
                                    viewModel.updateSyncFrequencyHours(hours)
                                }
                            },
                            valueRange = 1f..24f,
                            steps = 23, // 24 значения (1-24 часа)
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "1 час",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "24 часа",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatHours(hours: Int): String {
    return when {
        hours == 1 -> "1 час"
        hours < 24 -> "$hours часов"
        hours == 24 -> "1 день"
        else -> "${hours / 24} дней"
    }
} 