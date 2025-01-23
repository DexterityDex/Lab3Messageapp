package com.dexterity500.messageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dexterity500.messageapp.data.repository.NotificationRepository

class NotificationViewModelFactory(
    private val notificationRepository: NotificationRepository,
    private val notificationId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(notificationRepository, notificationId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}