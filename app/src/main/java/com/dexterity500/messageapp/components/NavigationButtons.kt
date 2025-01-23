package com.dexterity500.messageapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dexterity500.messageapp.ui.theme.LightGray
import com.dexterity500.messageapp.ui.theme.PurpleBorder

@Composable
fun UnreadNavigationButton(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 30.dp)
            .clickable {
                navController.navigate("unread_screen")
            }
            .background(LightGray) // Button color
            .border(width = 5.dp, color = PurpleBorder), // Button border
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Непрочитанные",
            fontSize = 24.sp
        )
    }
}

@Composable
fun ReadNavigationButton(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 30.dp)
            .clickable {
                navController.navigate("read_screen")
            }
            .background(LightGray) // Button color
            .border(width = 5.dp, color = PurpleBorder), // Button border
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Прочитанные",
            fontSize = 24.sp
        )
    }
}

@Composable
fun ProfileNavigationButton(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 30.dp)
            .clickable {
                navController.navigate("profile_screen")
            }
            .background(LightGray) // Button color
            .border(width = 5.dp, color = PurpleBorder), // Button border
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Профиль",
            fontSize = 24.sp
        )
    }
}

@Composable
fun LogOutNavigationButton(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 30.dp)
            .clickable {
                /*FirebaseAuth.getInstance().signOut() // sign out a user*/
                navController.navigate("login_screen")
            }
            .background(LightGray) // Button color
            .border(width = 5.dp, color = PurpleBorder), // Button border
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Выход",
            fontSize = 24.sp
        )
    }
}