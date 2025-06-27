package com.example.moneytalks.coreui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class TopAppBarState(
    val title: String? = null,
    val leadingIcon: Int? = null,
    val trailingIcon: Int? = null,
    val onLeadingIconClick: (() -> Unit)? = null,
    val onTrailingIconClick: (() -> Unit)? = null,
)

object TopAppBarStateProvider {
    var topAppBarState: TopAppBarState by mutableStateOf(TopAppBarState())
        private set

    fun update(topAppBarState: TopAppBarState) {
        this.topAppBarState = topAppBarState
    }
}