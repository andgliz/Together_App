package com.example.livingtogether.ui.today

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.HouseworkRepository
import com.example.livingtogether.data.repository.UsersHouseworkRepository
import com.example.livingtogether.ui.TodayUiState
import com.example.livingtogether.ui.toUsersHouseworkViewData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodayViewModel(
    private val authRepository: AuthRepository,
    private val usersHouseworkRepository: UsersHouseworkRepository,
    private val houseworkRepository: HouseworkRepository
) : ViewModel() {
    var total by mutableIntStateOf(0)
        private set

    private val _uiState: MutableStateFlow<TodayUiState> =
        MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    private var currentUserId by mutableStateOf(authRepository.currentUser!!.uid)

    init {
        initializeUiState(currentUserId)
        viewModelScope.launch {
            _uiState.collect { todayState ->
                if (todayState.housework.isNotEmpty()) {
                    total = todayState.housework.sumOf {
                        houseworkRepository.getHouseworkItem(it.id)!!.cost
                    }
                }
            }
        }

//        if (_uiState.value.housework.isNotEmpty()){
//            total = _uiState.value.housework. sumOf { it.housework.cost.toInt() }
//        }
    }

    private fun initializeUiState(currentUserId: String) {
        viewModelScope.launch {
            usersHouseworkRepository.getUsersHouseworkListFlow(currentUserId)
                .collect { usersHousework ->
                    _uiState.value =
                        TodayUiState(
                            housework = usersHousework.map {
                                houseworkRepository.getHouseworkItem(it.userId)!!
                                    .toUsersHouseworkViewData()
                            }
                        )
                }

        }
    }

}