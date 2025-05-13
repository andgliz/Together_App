package com.example.livingtogether.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.ui.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun signOut(onSuccess: () -> Unit) {
        authRepository.signOut()
        onSuccess()
    }

    fun onDeleteAccountClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.deleteUser(authRepository.currentUser!!.uid)
            authRepository.deleteAccount()
            onSuccess()
        }
    }
}
