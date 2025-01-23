package com.dexterity500.messageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.data.repository.UserRepository

class AddNotificationViewModelFactory(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddNotificationViewModel(notificationRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}