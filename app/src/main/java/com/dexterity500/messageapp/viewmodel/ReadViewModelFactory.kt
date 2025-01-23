package com.dexterity500.messageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dexterity500.messageapp.data.repository.NotificationRepository

class ReadViewModelFactory(
    private val notificationRepository: NotificationRepository,
    private val userUID: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReadViewModel(notificationRepository, userUID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

