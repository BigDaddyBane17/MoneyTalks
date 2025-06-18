package com.example.moneytalks.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem
import com.example.moneytalks.presentation.common.TopAppBarState
import com.example.moneytalks.presentation.common.TopAppBarStateProvider

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(SettingsIntent.LoadSettings)
    }

//    TopAppBarStateProvider.update(
//        TopAppBarState(
//            title = "Настройки"
//        )
//    )

    when (uiState) {
        is SettingsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is SettingsUiState.Success -> {
            val state = uiState as SettingsUiState.Success

            LazyColumn {
                item {
                    ListItem(
                        title = "Темная тема",
                        trailingComposable = {
                            Switch(
                                checked = state.isDarkMode,
                                onCheckedChange = {
                                    viewModel.handleIntent(
                                        SettingsIntent.ToggleDarkMode(it)
                                    )
                                }
                            )
                        },
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 16.dp),
                        modifier = Modifier.height(56.dp)
                    )
                    HorizontalDivider()
                }

                itemsIndexed(state.settingsList) { _, setting ->
                    ListItem(
                        title = setting,
                        trailingIcon = R.drawable.list_item,
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                        onClick = {
                            viewModel.handleIntent(
                                SettingsIntent.SelectSetting(setting)
                            )
                        },
                        modifier = Modifier.height(56.dp)
                    )
                    HorizontalDivider()
                }
            }
        }

        is SettingsUiState.Error -> {
            Text(
                text = (uiState as SettingsUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}