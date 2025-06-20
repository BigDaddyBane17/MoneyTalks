package com.example.moneytalks.presentation.analysis

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.TopAppBarState
import com.example.moneytalks.presentation.common.TopAppBarStateProvider

@Composable
fun AnalysisScreen(
    navController: NavHostController,
    type: String
) {

    Text(
        "АНАЛИЗ $type"
    )

    
}