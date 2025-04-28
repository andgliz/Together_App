package com.example.livingtogether.ui.rating

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.Family
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.RatingUiState
import com.example.livingtogether.ui.toUserViewData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RatingViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RatingUiState> = MutableStateFlow(RatingUiState())
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    private var currentUser by mutableStateOf(Firebase.auth.currentUser)

    init {
        currentUser = Firebase.auth.currentUser
        initializeUiState()
    }

    private suspend fun getCurrentUserFamily(): Family {
        return togetherRepository.getUserStream(currentUser?.email).family!!
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.value =
                RatingUiState(
                    usersList = togetherRepository.getUsersRatingStream(getCurrentUserFamily())
                        .map { it.toUserViewData() }
                )
        }
    }
}
