package com.example.livingtogether.ui.rating

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.RatingRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.ui.RatingUiState
import com.example.livingtogether.ui.UserToRatingData
import com.example.livingtogether.ui.toUserViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
            val usersToRating = usersList.map { user ->
                UserToRatingData(
                    userName = user.name,
                    totalRating = ratingRepository.getForUserWithDateRange(
                        user.id,
                        uiState.value.startDate,
                        uiState.value.endDate
                    ).sumOf { it.total }
                )
            }.sortedByDescending { it.totalRating }
            var rating = 0
            _uiState.value = _uiState.value.copy(
                userToRatings = usersToRating.map { user ->
                    rating++
                    user.copy(
                        ratingPlace = rating
                    )
                }
            )
        }
    }

    fun changeDate(start: Long, end: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                startDate = convertToDate(start),
                endDate = convertToDate(end)
            )
            initializeUiState(userRepository.getUser(authRepository.currentUser!!.uid)!!.family)
        }
    }

    private fun getDate(dayOfWeek: TemporalAdjuster): Date {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        return Date(
            LocalDate
                .now(ZoneId.systemDefault())
                .with(
                    dayOfWeek
                )
                .atStartOfDay(ZoneId.systemDefault())
                .format(formatter)
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertToDate(millis: Long): Date {
        val formatter = SimpleDateFormat("MM/dd/yyyy")
        return Date(formatter.format(millis))
    }
}
