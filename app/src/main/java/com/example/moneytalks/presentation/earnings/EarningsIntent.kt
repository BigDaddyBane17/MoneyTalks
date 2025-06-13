package com.example.moneytalks.presentation.earnings


sealed class EarningsIntent {
    object LoadEarnings: EarningsIntent()
    object OnItemClicked: EarningsIntent()
    object AddEarning: EarningsIntent()
    object GoToHistory: EarningsIntent()
}