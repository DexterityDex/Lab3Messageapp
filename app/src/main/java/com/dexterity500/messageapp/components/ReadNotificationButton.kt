package com.dexterity500.messageapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.dexterity500.messageapp.data.model.Notification

@Composable
fun ReadNotificationButton(
    notification: LiveData<Notification>,
    navController: NavController,
    userUID: String
) {
    val notificationState = notification.observeAsState()

    notificationState.value?.let { notification ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 30.dp)
                .clickable {
                    navController.navigate("notification_screen/$userUID/${notification.id}")
                }
                .background(Color(0xFFD0DADE))
                .border(width = 5.dp, color = Color(0xFF717DBB)),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Сообщение")
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(notification.title)
                }
            }
        }
    }
}
