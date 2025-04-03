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

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("MyLog", "Sign Up successful")
                    onSuccess()
                } else {
                    Log.d("MyLog", "Sign Up failure")
                }

            }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("MyLog", "Sign In successful")
                    onSuccess()
                } else {
                    Log.d("MyLog", "Sign In failure")
                }

            }
    }
}