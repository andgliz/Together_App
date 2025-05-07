package com.example.livingtogether.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.model.User
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.ui.LoginUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(emailInput: String) {
        _uiState.value = _uiState.value.copy(
            emailState = emailInput
        )
    }

    fun onPasswordChange(passwordInput: String) {
        _uiState.value = _uiState.value.copy(
            passwordState = passwordInput
        )
    }

    private fun onError(error: String) {
        _uiState.value = _uiState.value.copy(
            errorState = error
        )
    }

    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email and password cannot be empty.")
        } else {
            viewModelScope.launch {
                val result = authRepository.signUp(email, password)
                if (result.isNotBlank()) {
                    onError(result)
                } else {
                    onError("")
                    userRepository.createUser(User(id = Firebase.auth.currentUser!!.uid, email = email))
                    onSuccess()
                }
            }
        }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email and password cannot be empty.")
        } else {
            viewModelScope.launch {
                val result = authRepository.signIn(email, password)
                if (result.isNotBlank()) {
                    onError(result)
                } else {
                    onError("")
                    onSuccess()
                }
            }
        }
    }
}
