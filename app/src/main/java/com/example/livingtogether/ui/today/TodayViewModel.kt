package com.example.livingtogether.ui.today

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.TodayUiState
import com.example.livingtogether.ui.toUsersHouseworkViewData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodayViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {
    var total by mutableIntStateOf(0)
        private set

    private val _uiState: MutableStateFlow<TodayUiState> =
        MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    private var currentUser by mutableStateOf(Firebase.auth.currentUser)

    init {
        currentUser = Firebase.auth.currentUser
        initializeUiState(currentUser?.email)
        viewModelScope.launch {
            _uiState.collect { todayState ->
                if (todayState.housework.isNotEmpty()) {
                    total = todayState.housework. sumOf { it.housework.cost.toInt() }
                }
            }
        }

//        if (_uiState.value.housework.isNotEmpty()){
//            total = _uiState.value.housework. sumOf { it.housework.cost.toInt() }
//        }
    }

    private fun initializeUiState(email: String?) {
        viewModelScope.launch {
            _uiState.value =
                TodayUiState(
                    housework = togetherRepository.getUsersDataStream(togetherRepository.getUserStream(email = email))
                        .map { it.toUsersHouseworkViewData() }
                )
        }
    }

}