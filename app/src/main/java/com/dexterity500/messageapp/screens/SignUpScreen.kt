package com.dexterity500.messageapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dexterity500.messageapp.R
import com.dexterity500.messageapp.data.repository.UserRepository
import com.dexterity500.messageapp.ui.theme.TextColor
import com.dexterity500.messageapp.viewmodel.SignUpViewModel
import com.dexterity500.messageapp.viewmodel.SignUpViewModelFactory

@Composable
fun SignUpScreen(navController: NavHostController, userRepository: UserRepository) {
    val viewModel: SignUpViewModel = viewModel(
        factory = SignUpViewModelFactory(userRepository)
    )
    val emailState by viewModel.emailState
    val passwordState by viewModel.passwordState
    val nameState by viewModel.nameState
    val signUpResultState by viewModel.signUpResultState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4EEF9))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = (LocalConfiguration.current.screenHeightDp.dp * 1 / 3))
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            OutlinedTextField(
                value = emailState,
                onValueChange = { viewModel.onEmailChanged(it) },
                placeholder = { Text("Электронная почта") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = passwordState,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = { Text("Пароль") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = nameState,
                onValueChange = { viewModel.onNameChanged(it) },
                placeholder = { Text("Имя") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(Color.LightGray),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.onSignUpClicked()
                    }
                )
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = { viewModel.onSignUpClicked() },
                colors = ButtonDefaults.buttonColors(Color(0xFF4BB9E7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    "Зарегистрироваться",
                    color = TextColor,
                    fontSize = 20.sp
                )
            }
            if (signUpResultState.isNotEmpty()) {
                Text(
                    text = signUpResultState,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}