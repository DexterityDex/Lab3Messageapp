package com.dexterity500.messageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dexterity500.messageapp.screens.*
import com.dexterity500.messageapp.data.repository.UserRepository
import com.dexterity500.messageapp.data.repository.NotificationRepository

@Composable
fun AppNavGraph(navController: NavHostController, userRepository: UserRepository) {
    val notificationRepository = NotificationRepository()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController, userRepository)
        }
        composable("sign_up_screen") {
            SignUpScreen(navController, userRepository)
        }
        composable(
            route = "menu_screen/{userUID}",
            arguments = listOf(navArgument("userUID") { type = NavType.StringType })
        ) { backStackEntry ->
            val userUID = backStackEntry.arguments?.getString("userUID") ?: ""
            MenuScreen(navController, userUID)
        }
        composable(
            route = "unread_screen/{userUID}",
            arguments = listOf(navArgument("userUID") { type = NavType.StringType })
        ) { backStackEntry ->
            val userUID = backStackEntry.arguments?.getString("userUID") ?: ""
            UnreadScreen(navController, userUID, notificationRepository)
        }
        composable(
            route = "read_screen/{userUID}",
            arguments = listOf(navArgument("userUID") { type = NavType.StringType })
        ) { backStackEntry ->
            val userUID = backStackEntry.arguments?.getString("userUID") ?: ""
            ReadScreen(navController, userUID, notificationRepository)
        }
        composable("profile_screen") {
            ProfileScreen(navController)
        }
        composable(
            route = "notification_screen/{userUID}/{notificationId}",
            arguments = listOf(
                navArgument("userUID") { type = NavType.StringType },
                navArgument("notificationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userUID = backStackEntry.arguments?.getString("userUID") ?: ""
            val notificationId = backStackEntry.arguments?.getString("notificationId") ?: ""
            NotificationScreen(navController, userUID, notificationId, notificationRepository)
        }
        composable("add_notification_screen") {
            AddNotificationScreen(navController, notificationRepository, userRepository)
        }
    }
}
