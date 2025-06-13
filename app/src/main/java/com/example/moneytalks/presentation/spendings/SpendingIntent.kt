package com.example.moneytalks.presentation.spendings

sealed class SpendingIntent {
    object LoadExpenses: SpendingIntent()
    object OnItemClicked: SpendingIntent()
    object AddExpense: SpendingIntent()
    object GoToHistory: SpendingIntent()
}