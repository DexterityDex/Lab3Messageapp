package com.dexterity500.messageapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dexterity500.messageapp.R
import com.dexterity500.messageapp.ui.theme.DarkBlue
import com.dexterity500.messageapp.ui.theme.LightBlue
import com.dexterity500.messageapp.ui.theme.LightGray
import com.dexterity500.messageapp.ui.theme.PurpleBorder
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MenuScreen(navController: NavController, userUID: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(DarkBlue),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.returnbutton),
                contentDescription = "Return Button",
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = 35.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Меню",
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (-35).dp)
            )
        }

        // Menu Items
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
        ) {
            Column {
                // Написать сообщение
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 30.dp)
                        .clickable {
                            navController.navigate("add_notification_screen")
                        }
                        .background(LightGray)
                        .border(width = 5.dp, color = PurpleBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Написать сообщение",
                        fontSize = 24.sp
                    )
                }

                // Непрочитанные
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 30.dp)
                        .clickable {
                            navController.navigate("unread_screen/$userUID")
                        }
                        .background(LightGray)
                        .border(width = 5.dp, color = PurpleBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Непрочитанные",
                        fontSize = 24.sp
                    )
                }

                // Прочитанные
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 30.dp)
                        .clickable {
                            navController.navigate("read_screen/$userUID")
                        }
                        .background(LightGray)
                        .border(width = 5.dp, color = PurpleBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Прочитанные",
                        fontSize = 24.sp
                    )
                }

                // Выход
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 30.dp)
                        .clickable {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login_screen") {
                                popUpTo("login_screen") { inclusive = true }
                            }
                        }
                        .background(LightGray)
                        .border(width = 5.dp, color = PurpleBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Выход",
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}