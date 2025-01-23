package com.dexterity500.messageapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexterity500.messageapp.data.model.Notification
import com.dexterity500.messageapp.data.repository.NotificationRepository
import com.dexterity500.messageapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class AddNotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository // Добавляем UserRepository
) : ViewModel() {
    private val _titleState = mutableStateOf("")
    val titleState: State<String> = _titleState

    private val _messageState = mutableStateOf("")
    val messageState: State<String> = _messageState

    private val _hyperlinkTextState = mutableStateOf("")
    val hyperlinkTextState: State<String> = _hyperlinkTextState

    private val _hyperlinkAddressState = mutableStateOf("")
    val hyperlinkAddressState: State<String> = _hyperlinkAddressState

    private val _recipientEmailState = mutableStateOf("")
    val recipientEmailState: State<String> = _recipientEmailState

    private val _resultState = mutableStateOf("")
    val resultState: State<String> = _resultState

    fun onTitleChanged(newTitle: String) {
        _titleState.value = newTitle
    }

    fun onMessageChanged(newMessage: String) {
        _messageState.value = newMessage
    }

    fun onHyperlinkTextChanged(newHyperlinkText: String) {
        _hyperlinkTextState.value = newHyperlinkText
    }

    fun onHyperlinkAddressChanged(newHyperlinkAddress: String) {
        _hyperlinkAddressState.value = newHyperlinkAddress
    }

    fun onRecipientEmailChanged(newEmail: String) {
        _recipientEmailState.value = newEmail
    }

    fun onAddNotificationClicked() {
        if (recipientEmailState.value.isEmpty()) {
            _resultState.value = "Укажите email получателя"
            return
        }

        viewModelScope.launch {
            val recipientId = userRepository.getUserIdByEmail(recipientEmailState.value)
            if (recipientId == null) {
                _resultState.value = "Пользователь с указанным email не найден"
                return@launch
            }

            val notification = Notification(
                id = "",
                userUID = recipientId,
                title = titleState.value,
                message = messageState.value,
                hyperlinkText = hyperlinkTextState.value.takeIf { it.isNotEmpty() },
                hyperlinkAddress = hyperlinkAddressState.value.takeIf { it.isNotEmpty() },
                read = false
            )

            val result = notificationRepository.addNotification(notification)
            _resultState.value = if (result) "Сообщение успешно отправлено" else "Ошибка отправки сообщения"
        }
    }
}
