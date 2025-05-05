package com.example.livingtogether

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.data.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TogetherViewModel(
    private val togetherRepository: TogetherRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var currentUser by mutableStateOf(authRepository.currentUserIdFlow)
        private set

    var isUserInFamily by mutableStateOf(true)
        private set

    init {
        onChangeStatusOfAuth()
    }

    fun onChangeStatusOfAuth() {
        currentUser = authRepository.currentUserIdFlow
        viewModelScope.launch {
            if (currentUser.first() != null) {
                onChangeStatusOfFamily()
            }
        }
    }

    fun onChangeStatusOfFamily() {
        viewModelScope.launch {
            isUserInFamily = togetherRepository.getUserStream(authRepository.currentUser?.email).family != null
        }
    }
}
