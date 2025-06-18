package com.example.livingtogether.ui.family

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.model.Family
import com.example.livingtogether.data.model.User
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.FamilyRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.ui.FamilyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FamilyViewModel(
    private val authRepository: AuthRepository,
    private val familyRepository: FamilyRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var currentUser: User? by mutableStateOf(User())

    private val _uiState: MutableStateFlow<FamilyUiState> = MutableStateFlow(FamilyUiState())
    val uiState: StateFlow<FamilyUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            currentUser = userRepository.getUser(authRepository.currentUser!!.uid)
        }
    }

    fun onNameChange(nameInput: String) {
        _uiState.value = _uiState.value.copy(
            nameState = nameInput
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

    fun onJoinTheFamilyButtonClicked(
        name: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        if (name.isBlank() || password.isBlank()) {
            onError("Name and password cannot be empty.")
        } else {
            viewModelScope.launch {
                val familyId =
                    familyRepository.findFamily(name = name, password = password)
                if (familyId != null) {
                    userRepository.updateUser(
                        currentUser!!.copy(
                            family = familyId
                        )
                    )
                    onSuccess()
                } else {
                    onError("The family has not been found")
                }
            }
        }

    }

    fun onCreateTheFamilyButtonClicked(
        name: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        if (name.isBlank() || password.isBlank()) {
            onError("Name and password cannot be empty.")
        } else {
            viewModelScope.launch {
                val familyId =
                    familyRepository.createFamily(Family(name = name, password = password))
                userRepository.updateUser(currentUser!!.copy(family = familyId))
                onSuccess()
            }
        }
    }
}
