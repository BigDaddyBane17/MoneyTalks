package com.example.moneytalks


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moneytalks.presentation.navigation.MainAppScreen
import com.example.moneytalks.ui.theme.MoneyTalksTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyTalksTheme {
                MainAppScreen()
            }
        }
    }
}


