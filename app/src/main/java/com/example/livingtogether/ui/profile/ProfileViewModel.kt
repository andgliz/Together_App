package com.example.livingtogether.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.model.Family
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.FamilyRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.usecase.DeleteUserFromFamilyUseCase
import com.example.livingtogether.domain.usecase.DeleteUserUseCase
import com.example.livingtogether.ui.ProfileUiState
import com.example.livingtogether.ui.UserViewData
import com.example.livingtogether.ui.toUser
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val familyRepository: FamilyRepository,
    private val deleteUserFromFamilyUseCase: DeleteUserFromFamilyUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var currentUser = UserViewData()
    private var currentFamily = Family()

    init {
        viewModelScope.launch {
            val currentUserId = authRepository.currentUser!!.uid
            currentUser = userRepository.getUser(currentUserId)!!.toUserViewData()
            currentFamily = familyRepository.getFamily(currentUser.family)!!
            _uiState.value = ProfileUiState(
                userName = currentUser.name,
                familyName = currentFamily.name,
                user = currentUser.copy(
                    name = "",
                    family = "",
                )
            )
        }

    }

    private fun onError(error: String) {
        _uiState.value = _uiState.value.copy(
            errorState = error
        )
    }

    fun signOut(onSuccess: () -> Unit) {
        authRepository.signOut()
        onSuccess()
    }

    fun onChangeFamilyNameButtonClicked() {
        if (_uiState.value.user.family.isBlank()) {
            onError("Name cannot be empty.")
        } else {
            viewModelScope.launch {
                familyRepository.updateFamily(
                    currentFamily.copy(name = _uiState.value.user.family)
                )
                _uiState.value = _uiState.value.copy(
                    familyName = _uiState.value.user.family,
                    user = _uiState.value.user.copy(
                        family = ""
                    ),
                    errorState = "",
                )
            }
        }
    }

    fun onChangeNameButtonClicked() {
        if (_uiState.value.user.name.isBlank()) {
            onError("Name cannot be empty.")
        } else {
            viewModelScope.launch {
                userRepository.updateUser(
                    _uiState.value.user.toUser().copy(
                        family = currentFamily.id
                    )
                )
                _uiState.value = _uiState.value.copy(
                    userName = _uiState.value.user.name,
                    user = _uiState.value.user.copy(
                        name = "",
                    ),
                    errorState = "",
                )
            }
        }
    }

    fun onNameInputChanged(nameInput: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                name = nameInput,
            )
        )
    }

    fun onFamilyNameInputChanged(nameInput: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                family = nameInput,
            )
        )
    }

    fun onChangeFamilyClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            deleteUserFromFamilyUseCase(currentUser.toUser())
            onSuccess()
        }
    }

    fun onDeleteAccountClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            deleteUserUseCase(currentUser.id)
            onSuccess()
        }
    }
}
