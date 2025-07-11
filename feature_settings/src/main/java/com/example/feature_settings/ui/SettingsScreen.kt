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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.ListItem
import com.example.core_ui.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val settingsList = listOf(
        "Основной цвет",
        "Звуки",
        "Хаптики",
        "Код пароль",
        "Синхронизация",
        "Язык",
        "О программе"
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
                            checked = false,
                            onCheckedChange = {

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

            itemsIndexed(settingsList) { _, setting ->
                ListItem(
                    title = setting,
                    trailingIcon = R.drawable.list_item,
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    onClick = {

                    },
                    modifier = Modifier.height(56.dp)
                )
                HorizontalDivider()
            }
        }
    }

}