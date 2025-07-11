package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = { new ->
            query = new
            onSearch(new)
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFECE6F0)),
        placeholder = { Text("Найти статью") },
        trailingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Поиск")
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFECE6F0),
            focusedContainerColor   = Color(0xFFECE6F0),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor   = Color.Transparent
        )
    )
}


