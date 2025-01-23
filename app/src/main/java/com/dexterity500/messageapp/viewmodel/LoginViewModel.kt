package com.dexterity500.messageapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.dexterity500.messageapp.data.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginState = mutableStateOf("")
    val loginState: State<String> = _loginState

    private val _passwordState = mutableStateOf("")
    val passwordState: State<String> = _passwordState

    private val _loginResultState = mutableStateOf("")
    val loginResultState: State<String> = _loginResultState

    fun onLoginChanged(newLogin: String) {
        _loginState.value = newLogin
    }

    fun onPasswordChanged(newPassword: String) {
        _passwordState.value = newPassword
    }

    suspend fun onLoginClicked(navController: NavHostController) {
        val login = loginState.value
        val password = passwordState.value

        if (login.isEmpty() || password.isEmpty()) {
            _loginResultState.value = "Пожалуйста, заполните все поля"
            return
        }

        try {
            val user = userRepository.signInWithEmailAndPassword(login, password)
            if (user != null) {
                _loginResultState.value = "Авторизация успешна"
                navController.navigate("unread_screen/${user.uid}") {
                    popUpTo("login_screen") { inclusive = true }
                }
            } else {
                _loginResultState.value = "Неверный логин или пароль"
            }
        } catch (e: Exception) {
            _loginResultState.value = "Ошибка авторизации: ${e.message}"
        }
    }
}