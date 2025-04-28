package com.example.livingtogether.ui.family

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.Family
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.FamilyUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FamilyViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {
    private var currentUser by mutableStateOf(Firebase.auth.currentUser)

    private val _uiState: MutableStateFlow<FamilyUiState> = MutableStateFlow(FamilyUiState())
    val uiState: StateFlow<FamilyUiState> = _uiState.asStateFlow()

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

    fun onError(error: String) {
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
                if (togetherRepository.getFamilyDataStream(familyName = name, inputPassword = password)  != null) {
                    togetherRepository.addFamilyForUserStream(
                        family = togetherRepository.getFamilyDataStream(familyName = name, inputPassword = password),
                        email = currentUser?.email
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
                togetherRepository.insertIntoFamilyStream(Family(name = name, password = password))
                togetherRepository.addFamilyForUserStream(
                    family = Family(
                        name = name,
                        password = password
                    ),
                    email = currentUser?.email
                )
                onSuccess()
            }
        }
    }
}