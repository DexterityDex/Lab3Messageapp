package com.dexterity500.messageapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.dexterity500.messageapp.viewmodel.LoginViewModel
import com.dexterity500.messageapp.viewmodel.LoginViewModelFactory
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, userRepository: UserRepository) {
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(userRepository)
    )
    val loginState by viewModel.loginState
    val passwordState by viewModel.passwordState
    val loginResultState by viewModel.loginResultState
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        // Do nothing, thus preventing the back navigation
    }

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
                value = loginState,
                onValueChange = { viewModel.onLoginChanged(it) },
                placeholder = { Text("Логин") },
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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            viewModel.onLoginClicked(navController)
                        }
                    }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.onLoginClicked(navController)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF4BB9E7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    "Войти",
                    color = TextColor, // Dark blue color
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { navController.navigate("sign_up_screen") },
                colors = ButtonDefaults.buttonColors(Color(0xFF4BB9E7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    "Регистрация",
                    color = TextColor, // Dark blue color
                    fontSize = 20.sp
                )
            }
            if (loginResultState.isNotEmpty()) {
                Text(
                    text = loginResultState,
                    color = if (loginResultState.startsWith("Ошибка")) Color.Red else TextColor,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}