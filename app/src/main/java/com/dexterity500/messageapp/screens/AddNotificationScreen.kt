package com.dexterity500.messageapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dexterity500.messageapp.ui.theme.TextColor
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.data.repository.UserRepository
import com.dexterity500.messageapp.viewmodel.AddNotificationViewModel
import com.dexterity500.messageapp.viewmodel.AddNotificationViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddNotificationScreen(
    navController: NavHostController,
    notificationRepository: NotificationRepository,
    userRepository: UserRepository
) {
    val viewModel: AddNotificationViewModel = viewModel(
        factory = AddNotificationViewModelFactory(notificationRepository, userRepository)
    )

    val titleState by viewModel.titleState
    val messageState by viewModel.messageState
    val hyperlinkTextState by viewModel.hyperlinkTextState
    val hyperlinkAddressState by viewModel.hyperlinkAddressState
    val recipientEmailState by viewModel.recipientEmailState
    val resultState by viewModel.resultState

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4EEF9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = recipientEmailState,
                onValueChange = { viewModel.onRecipientEmailChanged(it) },
                placeholder = { Text("Email получателя") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = titleState,
                onValueChange = { viewModel.onTitleChanged(it) },
                placeholder = { Text("Заголовок") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = messageState,
                onValueChange = { viewModel.onMessageChanged(it) },
                placeholder = { Text("Сообщение") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = hyperlinkTextState,
                onValueChange = { viewModel.onHyperlinkTextChanged(it) },
                placeholder = { Text("Текст гиперссылки (необязательно)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = hyperlinkAddressState,
                onValueChange = { viewModel.onHyperlinkAddressChanged(it) },
                placeholder = { Text("Адрес гиперссылки (необязательно)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            viewModel.onAddNotificationClicked()
                        }
                    }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.onAddNotificationClicked()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF4BB9E7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    "Отправить сообщение",
                    color = TextColor,
                    fontSize = 20.sp
                )
            }

            if (resultState.isNotEmpty()) {
                Text(
                    text = resultState,
                    color = if (resultState.startsWith("Ошибка")) Color.Red else TextColor,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}