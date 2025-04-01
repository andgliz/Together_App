package com.example.livingtogether.ui.today

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.data.User
import com.example.livingtogether.ui.TodayUiState
import com.example.livingtogether.ui.toUsersHouseworkViewData
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

    init {
        initializeUiState()
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

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.value =
                TodayUiState(
                    housework = togetherRepository.getUsersDataStream(User(1, "user2", 30))
                        .map { it.toUsersHouseworkViewData() }
                )
        }
    }

}