package com.example.livingtogether.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.LoginUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

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

    fun onError(error: String) {
        _uiState.value = _uiState.value.copy(
            errorState = error
        )
    }

    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onSignUpFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onSignUpFailure("Email and password cannot be empty.")
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.value = _uiState.value.copy(
                            errorState = ""
                        )
                        onSuccess()
                    }
                }.addOnFailureListener {
                    onSignUpFailure(it.message ?: "Sign Up Error.")
                }
        }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onSignInFailure: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onSignInFailure("Email and password cannot be empty.")
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.value = _uiState.value.copy(
                            errorState = ""
                        )
                        onSuccess()
                    }
                }.addOnFailureListener {
                    onSignInFailure(it.message ?: "Sign In Error.")
                }
        }
    }
}