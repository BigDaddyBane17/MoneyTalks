package com.example.feature_account.models

import com.example.core_ui.R

object CurrencyProvider {
    
    val supportedCurrencies = listOf(
        Currency(
            code = "RUB",
            name = "Российский рубль",
            symbol = "₽",
            iconRes = R.drawable.mdi_ruble
        ),
        Currency(
            code = "USD",
            name = "Доллар США",
            symbol = "$",
            iconRes = R.drawable.mdi_dollar
        ),
        Currency(
            code = "EUR",
            name = "Евро",
            symbol = "€",
            iconRes = R.drawable.mdi_euro
        )
    )
} 