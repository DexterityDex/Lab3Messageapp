package com.dexterity500.messageapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dexterity500.messageapp.components.Header
import com.dexterity500.messageapp.components.UnreadNotificationButton
import com.dexterity500.messageapp.data.model.Notification
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.ui.theme.LightBlue

@Composable
fun UnreadScreen(navController: NavController, userUID: String, notificationRepository: NotificationRepository) {
    var notifications by remember { mutableStateOf(listOf<Notification>()) }
    LaunchedEffect(Unit) {
        notifications = notificationRepository.getNotificationsForUser(userUID).filter { !it.read }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue) // Lighter Blue color
    ) {
        Header(navController = navController, headerText = "Непрочитанные", userUID = userUID)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f) // Takes up 80% of the screen vertically
        ) {
            val scrollState = rememberScrollState() // Initialize scroll state
            Column(
                modifier = Modifier.verticalScroll(scrollState) // Make the column scrollable
            ) {
                if (notifications.isEmpty()) {
                    Text(
                        text = "Нет непрочитанных сообщений",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    for (notification in notifications) {
                        UnreadNotificationButton(notification, navController, userUID)
                    }
                }
            }
        }
    }
}