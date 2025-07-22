package com.example.livingtogether

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TogetherViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    var isUserAuthorized by mutableStateOf(authRepository.currentUser != null)
        private set

    var isUserInFamily by mutableStateOf(false)
        private set

    private var statusOfFamilyJob: Job? = null

    init {
        viewModelScope.launch {
            authRepository.currentUserIdFlow.collect { userId ->
                if (userId != null) {
                    isUserAuthorized = true
                    statusOfFamily(userId)
                } else {
                    statusOfFamilyJob?.cancel()
                    statusOfFamilyJob = null
                    isUserAuthorized = false
                    isUserInFamily = false
                }
            }
        }
    }

    private fun statusOfFamily(userId: String) {
        statusOfFamilyJob = viewModelScope.launch {
            userRepository.getUserFlow(userId).collect { user ->
                isUserInFamily = !user?.family.isNullOrEmpty()
            }
        }
    }
}
