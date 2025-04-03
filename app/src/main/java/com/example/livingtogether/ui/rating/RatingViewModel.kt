package com.example.livingtogether.ui.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.RatingUiState
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RatingViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RatingUiState> = MutableStateFlow(RatingUiState())
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.value =
                RatingUiState(
                    usersList = togetherRepository.getUsersRatingStream()
                        .map { it.toUserViewData() }
                )
        }
    }
}
