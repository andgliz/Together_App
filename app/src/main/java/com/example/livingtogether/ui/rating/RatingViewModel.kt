package com.example.livingtogether.ui.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.repository.AuthRepository
import com.example.livingtogether.data.repository.RatingRepository
import com.example.livingtogether.data.repository.UserRepository
import com.example.livingtogether.ui.RatingUiState
import com.example.livingtogether.ui.UserToRatingData
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.util.Date

class RatingViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RatingUiState> = MutableStateFlow(
        RatingUiState(
            startDate = getDate(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
            endDate = getDate(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        )
    )
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
            val usersList = userRepository
                .getAllFromFamily(currentUsersFamily = userFamilyId)
                .map { it.toUserViewData() }
            _uiState.value = _uiState.value.copy(
                userToRatings = usersList.map { user ->
                    UserToRatingData(
                        userName = user.name,
                        totalRating = ratingRepository.getForUserWithDateRange(
                            user.id,
                            uiState.value.startDate,
                            uiState.value.endDate
                        ).sumOf { it.total }
                    )
                }
            )

        }
    }

    fun changeDate(start: Date, end: Date) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                startDate = start,
                endDate = end
            )
            initializeUiState(userRepository.getUser(authRepository.currentUser!!.uid)!!.family)
        }
    }

    private fun getDate(dayOfWeek: TemporalAdjuster): Date {
        return Date(
            LocalDate
                .now(ZoneId.systemDefault())
                .with(
                    dayOfWeek
                )
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )
    }
}
