package com.example.moneytalks.presentation.item_expenses

sealed class ItemExpensesIntent {
    object LoadItemExpenses: ItemExpensesIntent()
    data class SearchItemExpenses(val query: String): ItemExpensesIntent()
}