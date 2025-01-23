package com.dexterity500.messageapp.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun Notification.toLiveData(): LiveData<Notification> {
    val liveData = MutableLiveData<Notification>()
    liveData.value = this
    return liveData
}