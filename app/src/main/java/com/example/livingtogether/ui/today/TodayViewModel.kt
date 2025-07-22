package com.example.livingtogether.ui.today

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.model.UsersHousework
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.HouseworkRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.domain.repository.UsersHouseworkRepository
import com.example.livingtogether.domain.usecase.GetUserHouseworkListUseCase
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.TodayUiState
import com.example.livingtogether.ui.toHouseworkViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class TodayViewModel(
    private val authRepository: AuthRepository,
    private val usersHouseworkRepository: UsersHouseworkRepository,
    private val houseworkRepository: HouseworkRepository,
    private val userRepository: UserRepository,
    private val getUserHouseworkListUseCase: GetUserHouseworkListUseCase
) : ViewModel() {
    var total by mutableIntStateOf(0)
        private set

    private val _uiState: MutableStateFlow<TodayUiState> =
        MutableStateFlow(TodayUiState(selectedDate = convertLocalToDate(LocalDate.now())))
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    private var currentUserId = authRepository.currentUser!!.uid

    init {
        viewModelScope.launch {
            uiState.flatMapLatest {
                initializeUiState(it.selectedDate)
            }.collect()
        }
    }

    private fun initializeUiState(date: String): Flow<List<UsersHousework>> {
        return usersHouseworkRepository.getUsersHouseworkListFlow(
            currentUserId,
            Date(date)
        )
            .onEach { userHouseworkList ->
                val getUserHouseworkList = getUserHouseworkListUseCase(userHouseworkList)
                _uiState.value = _uiState.value.copy(
                    userHouseworkList = getUserHouseworkList.map { it.toHouseworkViewData() },
                )
                total = if (getUserHouseworkList.isNotEmpty()) {
                    getUserHouseworkList.sumOf { it.cost }
                } else {
                    0
                }
            }
    }

    private fun updateUiState() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                houseworkList = houseworkRepository.getHouseworkList(
                    userRepository.getUser(
                        currentUserId
                    )!!.family,
                ).map { it.toHouseworkViewData() }
            )
        }
    }

    fun onChangeDate(date: Long) {
        _uiState.value = _uiState.value.copy(
            selectedDate = convertMillisToString(date)
        )
    }

    fun onPlusButtonClicked() {
        updateUiState()
    }

    fun onHouseworkClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            usersHouseworkRepository.createUsersHousework(
                UsersHousework(
                    userId = currentUserId,
                    houseworkId = housework.id,
                    date = Date(uiState.value.selectedDate),
                )
            )

        }
    }

    fun onDeleteClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            usersHouseworkRepository.deleteUsersHousework(housework.id)
        }
    }

    private fun convertLocalToDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        return date.format(formatter)
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertMillisToString(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy")
        return formatter.format(millis)
    }
}
