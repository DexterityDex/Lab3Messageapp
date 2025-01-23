package com.dexterity500.messageapp.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.dexterity500.messageapp.R
import com.dexterity500.messageapp.ui.theme.DarkBlue
import com.dexterity500.messageapp.ui.theme.TextColor

@Composable
fun Header(navController: NavController, userUID: String, headerText: String? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Takes up about 1/7th of the screen vertically
            .background(DarkBlue), // Darker Blue color
        contentAlignment = Alignment.CenterStart
    ) {
        MenuButton(navController, userUID)
        headerText?.let {
            Text(
                text = it,
                color = TextColor,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-35).dp) // Move the logo image 35 pixels to the left
        )
    }
}