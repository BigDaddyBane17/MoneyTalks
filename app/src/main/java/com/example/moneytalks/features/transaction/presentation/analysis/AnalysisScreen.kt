package com.example.moneytalks.features.transaction.presentation.analysis

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AnalysisScreen(
    navController: NavHostController,
    type: String
) {

    Text(
        "АНАЛИЗ $type"
    )

    
}