package com.example.livingtogether.ui.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.ui.RatingUiState
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RatingViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RatingUiState> = MutableStateFlow(RatingUiState())
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUserFamily =
                userRepository.getUser(authRepository.currentUser!!.uid)!!.family
            initializeUiState(currentUserFamily)
        }
    }

    private fun initializeUiState(userFamilyId: String) {
        viewModelScope.launch {
            userRepository.getUsersFromFamilyList(userFamilyId).collect { usersList ->
                _uiState.value =
                    RatingUiState(
                        usersList = usersList.map { it.toUserViewData() }
                    )
            }
        }
    }
}
