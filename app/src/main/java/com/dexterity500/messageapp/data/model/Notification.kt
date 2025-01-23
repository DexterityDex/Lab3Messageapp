package com.dexterity500.messageapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val id: String = "",
    val userUID: String = "",  // ID получателя сообщения
    val title: String = "",
    val message: String = "",
    val hyperlinkText: String? = null,
    val hyperlinkAddress: String? = null,
    var read: Boolean = false
) : Parcelable {
    // Required no-argument constructor
    constructor() : this("", "", "", "", null, null, false)
}
