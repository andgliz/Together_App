package com.example.livingtogether.ui.today

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.model.Rating
import com.example.livingtogether.data.model.UsersHousework
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.HouseworkRepository
import com.example.livingtogether.data.repository.RatingRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.data.repository.UsersHouseworkRepository
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.TodayUiState
import com.example.livingtogether.ui.toHouseworkViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodayViewModel(
    private val authRepository: AuthRepository,
    private val usersHouseworkRepository: UsersHouseworkRepository,
    private val houseworkRepository: HouseworkRepository,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository
) : ViewModel() {
    var total by mutableIntStateOf(0)
        private set

    private val _uiState: MutableStateFlow<TodayUiState> =
        MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    private var currentUserId by mutableStateOf(authRepository.currentUser!!.uid)
    private var currentDate by mutableStateOf(Date(convertMillisToDate(System.currentTimeMillis())))

    init {
        initializeUiState()
        viewModelScope.launch {
            _uiState.collect { todayState ->
                if (todayState.housework.isNotEmpty()) {
                    total = todayState.housework.sumOf {
                        houseworkRepository.getHouseworkItem(it.houseworkId)!!.cost
                    }
                }
            }
        }

    }

    private fun initializeUiState() {
        viewModelScope.launch {
            usersHouseworkRepository.getUsersHouseworkListFlow(currentUserId, currentDate)
                .collect { usersHousework ->
                    _uiState.value = _uiState.value.copy(housework = usersHousework.map {
                        HouseworkViewData(
                            id = it.id,
                            houseworkId = it.houseworkId,
                            name = houseworkRepository.getHouseworkItem(it.houseworkId)!!.name,
                            cost = houseworkRepository.getHouseworkItem(it.houseworkId)!!.cost.toString()
                        )
                    })
                }
        }
    }

    private fun updateUiState() {
        viewModelScope.launch {
            houseworkRepository.getHouseworkListFlow(userRepository.getUser(currentUserId)!!.family)
                .collect { housework ->
                    _uiState.value = _uiState.value.copy(
                        houseworkList = housework.map { it.toHouseworkViewData() }
                    )
                }
        }
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
                    date = currentDate
                )
            )
            ratingRepository.addForDate(
                Rating(
                    userId = currentUserId,
                    date = currentDate,
                    total = housework.cost.toInt()
                )
            )
        }
    }

    fun onDeleteClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            usersHouseworkRepository.deleteUsersHousework(housework.id)
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(millis)
    }
}
