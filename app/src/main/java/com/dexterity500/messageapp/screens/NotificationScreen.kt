package com.dexterity500.messageapp.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dexterity500.messageapp.NotificationViewModel
import com.dexterity500.messageapp.NotificationViewModelFactory
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.ui.theme.LightBlue
import com.dexterity500.messageapp.ui.theme.LightGray
import com.dexterity500.messageapp.ui.theme.PurpleBorder
import com.dexterity500.messageapp.ui.theme.TextColor
import com.dexterity500.messageapp.components.Header

@Composable
fun NotificationScreen(navController: NavController, userUID: String, notificationId: String, notificationRepository: NotificationRepository) {
    val viewModel: NotificationViewModel = viewModel(factory = NotificationViewModelFactory(notificationRepository, notificationId))
    val notificationState by viewModel.notificationState.collectAsState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(notificationId) {
        Log.d("NotificationScreen", "Loading notification with ID: $notificationId")
        viewModel.loadNotification(notificationId)
        viewModel.markNotificationAsRead(notificationId)
    }

    Log.d("NotificationScreen", "Notification state: $notificationState")

    notificationState?.let { notification ->
        Log.d("NotificationScreen", "Notification loaded: ${notification.title}")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlue) // Lighter Blue color
                .verticalScroll(scrollState)
        ) {
            Header(navController = navController, headerText = null, userUID = userUID) // Change headerText as needed
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes up the remaining space
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .background(LightGray) // Button color
                        .border(width = 5.dp, color = PurpleBorder)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        Text(
                            text = notification.title,
                            color = TextColor,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = notification.message,
                            color = TextColor
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        notification.hyperlinkText?.let {
                            Text(
                                text = it,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    Log.d("NotificationScreen", "Opening hyperlink: ${notification.hyperlinkAddress}")
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(notification.hyperlinkAddress))
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    } ?: run {
        Log.d("NotificationScreen", "Notification is null")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading notification...", color = TextColor, fontSize = 20.sp)
        }
    }
}
