package com.dexterity500.messageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dexterity500.messageapp.data.repository.NotificationRepository

class UnreadViewModelFactory(
    private val notificationRepository: NotificationRepository,
    private val userUID: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnreadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnreadViewModel(notificationRepository, userUID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

