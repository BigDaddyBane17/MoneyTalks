package com.example.moneytalks.features.account.presentation.edit_account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytalks.R
import com.example.moneytalks.features.account.presentation.account.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    viewModel: EditAccountViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel,
    onBack: () -> Unit
) {
    val account by viewModel.account.collectAsStateWithLifecycle()
    var name by remember(account) { mutableStateOf(account?.name.orEmpty()) }
    var balance by remember(account) { mutableStateOf(account?.balance.orEmpty()) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedAccount by accountViewModel.selectedAccount.collectAsStateWithLifecycle()

    LaunchedEffect(selectedAccount?.id) {
        viewModel.setAccount(selectedAccount)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Редактирование счета") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(id = R.drawable.cancel), contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.updateAccount(name, balance)
                            onBack()
                        },
                        enabled = (name.isNotBlank() && name != account?.name) ||
                                (balance.isNotBlank() && balance != account?.balance)
                    ) {
                        Icon(painterResource(id = R.drawable.ok), contentDescription = "Подтвердить")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название счета") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            OutlinedTextField(
                value = balance,
                onValueChange = { balance = it },
                label = { Text("Баланс") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (uiState is EditAccountUiState.Error) {
                Text(
                    text = (uiState as EditAccountUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

