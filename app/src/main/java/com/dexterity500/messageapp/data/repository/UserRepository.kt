package com.dexterity500.messageapp.data.repository

import android.util.Log
import com.dexterity500.messageapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://ap-trade-notification-app-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val usersRef = database.getReference("users")
    private val notificationRepository = NotificationRepository()

    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.e("UserRepository", "Error signing in: ${e.message}")
            null
        }
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String, name: String): Boolean {
        try {
            // Создаем пользователя в Authentication
            Log.d("UserRepository", "Starting user creation in Authentication")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid

            if (userId == null) {
                Log.e("UserRepository", "Failed to get userId after user creation")
                return false
            }

            // Создаем объект пользователя для базы данных
            val newUser = User(
                uid = userId,
                email = email,
                name = name
            )

            // Сохраняем пользователя в Realtime Database
            Log.d("UserRepository", "Saving user to Realtime Database")
            try {
                usersRef.child(userId).setValue(newUser).await()
                Log.d("UserRepository", "Successfully saved user to database")
            } catch (e: Exception) {
                Log.e("UserRepository", "Error saving user to database: ${e.message}")
                // В случае ошибки сохранения в базу данных, удаляем пользователя из Authentication
                auth.currentUser?.delete()
                return false
            }

            // Создаем приветственное сообщение
            Log.d("UserRepository", "Creating welcome notification")
            try {
                notificationRepository.createDefaultNotificationForUser(userId)
                Log.d("UserRepository", "Successfully created welcome notification")
            } catch (e: Exception) {
                Log.e("UserRepository", "Error creating welcome notification: ${e.message}")
                // Не возвращаем false здесь, так как пользователь уже создан
            }

            return true
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in sign up process: ${e.message}")
            return false
        }
    }

    suspend fun getUserIdByEmail(email: String): String? {
        return try {
            Log.d("UserRepository", "Searching for user with email: $email")
            val snapshot = usersRef.get().await()
            var userId: String? = null

            snapshot.children.forEach { child ->
                val user = child.getValue(User::class.java)
                if (user?.email == email) {
                    userId = child.key
                    return@forEach
                }
            }

            if (userId == null) {
                Log.d("UserRepository", "User not found with email: $email")
            } else {
                Log.d("UserRepository", "Found user with ID: $userId")
            }

            userId
        } catch (e: Exception) {
            Log.e("UserRepository", "Error searching for user: ${e.message}")
            null
        }
    }
}