package com.example.feature_settings.ui.haptics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.feature_settings.models.HapticMode
import com.example.feature_settings.ui.getSettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapticsScreen(
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Хаптики") },
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
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Включить хаптики",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.updateHapticEnabled(!uiState.hapticEnabled) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Switch(
                                checked = uiState.hapticEnabled,
                                onCheckedChange = { viewModel.updateHapticEnabled(it) }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Вибрация при взаимодействии",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            if (uiState.hapticEnabled) {
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
                                text = "Интенсивность",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            HapticMode.values().forEach { hapticMode ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.updateHapticMode(hapticMode) }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = uiState.hapticMode == hapticMode,
                                        onClick = { viewModel.updateHapticMode(hapticMode) }
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = hapticMode.displayName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 