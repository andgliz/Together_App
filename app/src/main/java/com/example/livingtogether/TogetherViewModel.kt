package com.example.livingtogether

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class TogetherViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    var currentUser by mutableStateOf(auth.currentUser)
        private set

    var isUserInFamily by mutableStateOf(true)
        private set

    init {
        onChangeStatusOfAuth()
    }

    fun onChangeStatusOfAuth() {
        currentUser = auth.currentUser
        if (currentUser != null) {
            onChangeStatusOfFamily()
        }
    }

    fun onChangeStatusOfFamily() {
        viewModelScope.launch {
            isUserInFamily = togetherRepository.getUserStream(currentUser?.email).family != null
        }
    }
}
