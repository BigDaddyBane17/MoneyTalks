package com.example.feature_settings.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.di.ComponentProvider
import com.example.core.di.FeatureComponentProvider
import com.example.core_ui.components.ListItem
import com.example.core_ui.R
import com.example.feature_settings.ui.getSettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToTheme: () -> Unit = {},
    onNavigateToHaptics: () -> Unit = {},
    onNavigateToPinCode: () -> Unit = {},
    onNavigateToSync: () -> Unit = {},
    onNavigateToLanguage: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {}
) {
    val viewModel: SettingsViewModel = viewModel(
        factory = getSettingsViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val settingsList = listOf(
        "Основной цвет" to onNavigateToTheme,
        "Хаптики" to onNavigateToHaptics,
        "Код пароль" to onNavigateToPinCode,
        "Синхронизация" to onNavigateToSync,
        "Язык" to onNavigateToLanguage,
        "О программе" to onNavigateToAbout
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Настройки") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            Modifier.padding(innerPadding)
        ) {
            item {
                ListItem(
                    title = "Темная тема",
                    trailingComposable = {
                        Switch(
                            checked = uiState.themeMode == com.example.feature_settings.models.ThemeMode.DARK,
                            onCheckedChange = { isDark ->
                                val newThemeMode = if (isDark) {
                                    com.example.feature_settings.models.ThemeMode.DARK
                                } else {
                                    com.example.feature_settings.models.ThemeMode.LIGHT
                                }
                                viewModel.updateThemeMode(newThemeMode)
                            }
                        )
                    },
                    contentPadding = PaddingValues(
                        vertical = 2.dp,
                        horizontal = 16.dp
                    ),
                    modifier = Modifier.height(56.dp)
                )
                HorizontalDivider()
            }

            itemsIndexed(settingsList) { _, (setting, onClick) ->
                ListItem(
                    title = setting,
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    onClick = onClick,
                    modifier = Modifier.height(56.dp),
                    trailingIcon = R.drawable.more_vert
                )
                HorizontalDivider()
            }
        }
    }
}