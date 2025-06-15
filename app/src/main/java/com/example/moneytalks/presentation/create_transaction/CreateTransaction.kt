package com.example.moneytalks.presentation.create_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moneytalks.R
import com.example.moneytalks.presentation.common.ListItem

@Composable
fun CreateTransaction(
    navController: NavHostController,
    type: String
) {


    Column {
        ListItem(
            title = "Счет",
            amount = "Сбербанк", // выбираем счет сами
            onClick = {

            },
            modifier = Modifier,
            trailingIcon = R.drawable.more_vert
        )
        HorizontalDivider()
        ListItem(
            title = "Статья",
            amount = "Ремонт", // выбираем сами
            onClick = {

            },
            modifier = Modifier,
            trailingIcon = R.drawable.more_vert
        )
        HorizontalDivider()
        ListItem(
            title = "Сумма",
            amount = "25 270", // выбираем счет сами
            currency = "₽",
            onClick = {

            },
            modifier = Modifier,

        )
        HorizontalDivider()
        ListItem(
            title = "Дата",
            amount = "25.03.2025", // выбираем счет сами
            onClick = {

            },
            modifier = Modifier,

        )
        HorizontalDivider()
        ListItem(
            title = "Время",
            amount = "04:20", // выбираем счет сами
            onClick = {

            },
            modifier = Modifier,

        )
        HorizontalDivider()
        ListItem(
            title = "Комментарий",
            onClick = {

            },
            modifier = Modifier,
        )
        HorizontalDivider()


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
            ,
            onClick = {

            },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE46962)
            ),
            content = {
                Text(
                    text = "Удалить расход",
                    color = Color(0xFFFFFFFF)
                )
            }
        )
    }
}