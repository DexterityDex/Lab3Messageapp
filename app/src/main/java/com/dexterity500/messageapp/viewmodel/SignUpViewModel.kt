package com.dexterity500.messageapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexterity500.messageapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _emailState = mutableStateOf("")
    val emailState: State<String> = _emailState

    private val _passwordState = mutableStateOf("")
    val passwordState: State<String> = _passwordState

    private val _nameState = mutableStateOf("")
    val nameState: State<String> = _nameState

    private val _signUpResultState = mutableStateOf("")
    val signUpResultState: State<String> = _signUpResultState

    fun onEmailChanged(newEmail: String) {
        _emailState.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _passwordState.value = newPassword
    }

    fun onNameChanged(newName: String) {
        _nameState.value = newName
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            val email = emailState.value
            val password = passwordState.value
            val name = nameState.value

            try {
                val result = userRepository.signUpWithEmailAndPassword(email, password, name)
                if (result) {
                    _signUpResultState.value = "Регистрация прошла успешно."
                } else {
                    _signUpResultState.value = "Ошибка регистрации."
                }
            } catch (e: Exception) {
                _signUpResultState.value = "Ошибка регистрации: ${e.message}"
            }
        }
    }
}