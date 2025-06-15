package com.example.moneytalks.presentation.history

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.AppBarProvider

object HistoryTopBar : AppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun provideTopBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = {
                Text("Моя история")
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF2AE881)
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Назад"
                    )
                }
            },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.history),
                        contentDescription = "История"
                    )
                }
            }
        )
    }
}