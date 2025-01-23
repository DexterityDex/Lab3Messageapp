package com.dexterity500.messageapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dexterity500.messageapp.components.Header
import com.dexterity500.messageapp.components.ReadNotificationButton
import com.dexterity500.messageapp.data.model.toLiveData
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.ui.theme.LightBlue
import com.dexterity500.messageapp.viewmodel.ReadViewModel
import com.dexterity500.messageapp.viewmodel.ReadViewModelFactory

@Composable
fun ReadScreen(
    navController: NavController,
    userUID: String,
    notificationRepository: NotificationRepository
) {
    val viewModel: ReadViewModel = viewModel(
        factory = ReadViewModelFactory(notificationRepository, userUID)
    )
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Header(navController = navController, headerText = "Прочитанные", userUID = userUID)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                if (notifications.isEmpty()) {
                    Text(
                        text = "Нет прочитанных сообщений",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    notifications.forEach { notification ->
                        ReadNotificationButton(
                            notification = notification.toLiveData(),
                            navController = navController,
                            userUID = userUID
                        )
                    }
                }
            }
        }
    }
}