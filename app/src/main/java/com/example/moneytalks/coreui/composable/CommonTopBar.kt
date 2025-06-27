package com.example.moneytalks.coreui.composable

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.moneytalks.coreui.TopAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: TopAppBarState = TopAppBarState()
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            state.leadingIcon?.let { leadIcon ->
                IconButton(
                    onClick = { state.onLeadingIconClick?.invoke() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(leadIcon),
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            state.title?.let { title ->
                Text(text = title)
            }
        },
        actions = {
            state.trailingIcon?.let { trailIcon ->
                IconButton(
                    onClick = { state.onTrailingIconClick?.invoke() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(trailIcon),
                        contentDescription = null,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2AE881)
        )
    )
}
