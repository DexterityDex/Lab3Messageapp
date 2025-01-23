package com.dexterity500.messageapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dexterity500.messageapp.R

@Composable
fun MenuButton(navController: NavController, userUID: String) {
    Image(
        painter = painterResource(id = R.drawable.menubutton),
        contentDescription = "Кнопка меню",
        modifier = Modifier
            .size(50.dp)
            .offset(x = 35.dp) // Move the button's image 35 pixels to the right
            .clickable { navController.navigate("menu_screen/$userUID") }
    )
}