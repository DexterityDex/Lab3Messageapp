package com.dexterity500.messageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexterity500.messageapp.data.model.Notification
import com.dexterity500.messageapp.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UnreadViewModel(private val notificationRepository: NotificationRepository, private val userUID: String) : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    init {
        loadUnreadNotifications()
    }

    private fun loadUnreadNotifications() {
        viewModelScope.launch {
            val allNotifications = notificationRepository.getNotificationsForUser(userUID)
            _notifications.value = allNotifications.filter { !it.read }
        }
    }
}
