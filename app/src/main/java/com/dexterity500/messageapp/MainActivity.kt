package com.dexterity500.messageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.dexterity500.messageapp.navigation.AppNavGraph
import com.dexterity500.messageapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepository = UserRepository()

        setContent {
            MainScreen(userRepository)
        }
    }
}

@Composable
fun MainScreen(userRepository: UserRepository) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                // Если пользователь авторизован, сразу переходим к экрану непрочитанных сообщений
                navController.navigate("unread_screen/${currentUser.uid}") {
                    popUpTo("login_screen") { inclusive = true }
                }
            } else {
                // Если пользователь не авторизован, переходим к экрану входа
                navController.navigate("login_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
            }
        }
    }

    AppNavGraph(navController = navController, userRepository = userRepository)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(UserRepository())
}