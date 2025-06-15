package com.example.moneytalks.presentation.item_expenses

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moneytalks.presentation.common.AppBarProvider

object ItemExpenseTopBar : AppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun provideTopBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = {
                Text("Мои статьи")
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF2AE881)
            )
        )
    }
}
