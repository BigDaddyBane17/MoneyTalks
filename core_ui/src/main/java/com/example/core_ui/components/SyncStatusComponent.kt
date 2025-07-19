package com.example.core_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun SyncStatusComponent(
    isSyncing: Boolean,
    hasUnsyncedData: Boolean,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isSyncing) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(4.dp),
                    strokeWidth = 2.dp
                )
            } else if (hasUnsyncedData) {
                Icon(
                    painter = painterResource(id = R.drawable.sync),
                    contentDescription = "Unsynced data",
                    tint = MaterialTheme.colorScheme.error
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.sync),
                    contentDescription = "Synced",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = when {
                    isSyncing -> "Синхронизация..."
                    hasUnsyncedData -> "Есть несинхронизированные данные"
                    else -> "Все синхронизировано"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        if (hasUnsyncedData && !isSyncing) {
            androidx.compose.material3.TextButton(
                onClick = onSyncClick
            ) {
                Text("Синхронизировать")
            }
        }
    }
} 