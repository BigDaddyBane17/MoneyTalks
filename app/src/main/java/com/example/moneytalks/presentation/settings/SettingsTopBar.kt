package com.example.moneytalks.presentation.settings

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.moneytalks.presentation.common.AppBarProvider

object SettingsTopBar : AppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun provideTopBar() {
        CenterAlignedTopAppBar(
            title = {
                Text("Настройки")
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF2AE881)
            )
        )
    }
}
