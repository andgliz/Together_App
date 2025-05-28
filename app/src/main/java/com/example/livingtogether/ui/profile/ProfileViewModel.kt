package com.example.livingtogether.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.ui.ProfileUiState
import com.example.livingtogether.ui.toUser
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUserId = authRepository.currentUser!!.uid
            val currentUser = userRepository.getUser(currentUserId)!!.toUserViewData()
            _uiState.value = ProfileUiState(
                userName = currentUser.name,
                user = currentUser
            )
        }

    }

    fun signOut(onSuccess: () -> Unit) {
        authRepository.signOut()
        onSuccess()
    }

    fun onChangeNameButtonClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                userName = _uiState.value.user.name
            )
            userRepository.updateUser(
                _uiState.value.user.toUser()
            )
        }
    }

    fun onNameInputChanged(nameInput: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                name = nameInput
            )
        )
    }

    fun onDeleteAccountClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.deleteUser(authRepository.currentUser!!.uid)
            authRepository.deleteAccount()
            onSuccess()
        }
    }
}
