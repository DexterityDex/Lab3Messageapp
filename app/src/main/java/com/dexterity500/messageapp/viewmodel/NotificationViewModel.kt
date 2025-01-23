package com.dexterity500.messageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexterity500.messageapp.data.model.Notification
import com.dexterity500.messageapp.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class NotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val notificationId: String
) : ViewModel() {

    private val _notificationState = MutableStateFlow<Notification?>(null)
    val notificationState: StateFlow<Notification?> = _notificationState

    fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            notificationRepository.markNotificationAsRead(notificationId)
        }
    }

    fun loadNotification(notificationId: String) {
        viewModelScope.launch {
            Log.d("NotificationViewModel", "Fetching notification with ID: $notificationId")
            try {
                val notification = notificationRepository.getNotificationById(notificationId)
                Log.d("NotificationViewModel", "Notification fetched: $notification")
                _notificationState.value = notification
            } catch (e: Exception) {
                Log.e("NotificationViewModel", "Error fetching notification", e)
            }
        }
    }
}

