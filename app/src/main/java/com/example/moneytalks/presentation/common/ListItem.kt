package com.example.moneytalks.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListItem(
    leadingIcon: String? = null,
    title: String,
    description: String? = null,
    amount: String? = null,
    currency: String? = null,
    trailingIcon: Int? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
    backgroundColor: Color = Color(0xFFFeF7FF),
    trailingComposable: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                onClick?.let {
                    Modifier.clickable {
                        it()
                    }
                } ?: Modifier
            )
            .background(backgroundColor)
            .padding(contentPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
//                Image(
//                    painter = painterResource(leadingIcon),
//                    contentDescription = "Leading Icon",
//                    modifier = Modifier.size(24.dp)
//                )

                Text(
                    text = leadingIcon,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

            Column {
                Text (
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )

                if(description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF49454F)
                    )
                }
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(amount != null) {
                Text(
                    text = if (currency != null) "$amount $currency" else amount
                )
            }

            if(trailingIcon != null) {
                Spacer(Modifier.width(16.dp))
                Icon(
                    painter = painterResource(trailingIcon),
                    contentDescription = "TrailIcon"
                )
            }

            if(trailingComposable != null) {
                trailingComposable()
            }
        }


    }

}