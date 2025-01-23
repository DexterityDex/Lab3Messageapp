package com.dexterity500.messageapp.data.repository

import android.util.Log
import com.dexterity500.messageapp.data.model.Notification
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class NotificationRepository {
    private val database = FirebaseDatabase.getInstance("https://ap-trade-notification-app-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val notificationsRef = database.getReference("notifications")

    suspend fun getNotificationsForUser(userUID: String): List<Notification> {
        val snapshot = notificationsRef.orderByChild("userUID").equalTo(userUID).get().await()
        return snapshot.children.mapNotNull { it.getValue(Notification::class.java) }
    }

    suspend fun saveNotification(notification: Notification): Boolean {
        return try {
            val notificationRef = notificationsRef.push()
            notificationRef.setValue(notification).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addNotification(notification: Notification): Boolean {
        return try {
            val newRef = notificationsRef.push()
            newRef.setValue(notification.copy(id = newRef.key!!)).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getNotificationById(notificationId: String): Notification? {
        return try {
            val snapshot = notificationsRef.orderByChild("id").equalTo(notificationId).get().await()
            if (snapshot.exists()) {
                val notificationSnapshot = snapshot.children.first()
                notificationSnapshot.getValue(Notification::class.java)
            } else {
                Log.d("NotificationRepository", "Notification with ID $notificationId does not exist")
                null
            }
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error loading notification", e)
            null
        }
    }

    suspend fun markNotificationAsRead(notificationId: String) {
        try {
            val notificationRef = notificationsRef.orderByChild("id").equalTo(notificationId).get().await()
            if (notificationRef.exists()) {
                val notificationSnapshot = notificationRef.children.first()
                notificationSnapshot.ref.updateChildren(mapOf("read" to true)).await()
                Log.d("NotificationRepository", "Notification $notificationId marked as read")
            } else {
                Log.d("NotificationRepository", "Notification $notificationId not found")
            }
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error marking notification as read", e)
        }
    }

    suspend fun createDefaultNotificationForUser(userUID: String) {
        val defaultNotification = Notification(
            id = "",
            userUID = userUID,
            title = "Добро пожаловать в приложение!",
            message = "Ваш аккаунт был успешно создан. Теперь вы можете отправлять и получать сообщения.",
            read = false
        )
        saveNotification(defaultNotification.copy(id = notificationsRef.push().key!!))
    }
}
