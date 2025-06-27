package com.example.moneytalks.features.categories.presentation.item_expenses

sealed class ItemExpensesIntent {
    object LoadItemExpenses: ItemExpensesIntent()
    data class SearchItemExpenses(val query: String): ItemExpensesIntent()
}