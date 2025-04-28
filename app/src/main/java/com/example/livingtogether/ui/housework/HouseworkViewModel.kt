package com.example.livingtogether.ui.housework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.Family
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.HouseworkUiState
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.toHousework
import com.example.livingtogether.ui.toHouseworkViewData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HouseworkViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {
    private var currentUser by mutableStateOf(Firebase.auth.currentUser)

    private val _uiState: MutableStateFlow<HouseworkUiState> = MutableStateFlow(HouseworkUiState())
    val uiState: StateFlow<HouseworkUiState> = _uiState.asStateFlow()

    init {
        initializeUiState()
    }

    private suspend fun getCurrentUserFamily(): Family {
        return togetherRepository.getUserStream(currentUser?.email).family!!
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.value =
                HouseworkUiState(
                    houseworkList = togetherRepository.getHouseworkStream(getCurrentUserFamily())
                        .map { it.toHouseworkViewData() }
                )
        }
    }

    fun updateUiState(housework: HouseworkViewData) {
        viewModelScope.launch {
            _uiState.value = HouseworkUiState(
                houseworkInput = housework,
                isEnabled = isValidInput(housework),
                houseworkList = togetherRepository.getHouseworkStream(getCurrentUserFamily())
                    .map { it.toHouseworkViewData() }
            )
        }

    }

    fun onAddHouseworkClicked() {
        viewModelScope.launch {
            togetherRepository.insertIntoHouseworkStream(
                _uiState.value.houseworkInput.toHousework(getCurrentUserFamily())
            )
            initializeUiState()
        }
    }

    fun onDeleteHouseworkClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            togetherRepository.deleteFromHouseworkStream(
                housework.toHousework(getCurrentUserFamily())
            )
            initializeUiState()
        }
    }

    fun onHouseworkClicked() {
        viewModelScope.launch {
            togetherRepository.updateHouseworkStream(
                _uiState.value.houseworkInput.toHousework(getCurrentUserFamily())
            )
            initializeUiState()
        }
    }

    private fun isValidInput(uiState: HouseworkViewData = _uiState.value.houseworkInput): Boolean {
        return with(uiState) {
            name.isNotBlank() && cost.isNotEmpty() && cost.intOrNot() && cost.toInt() > 0
        }
    }

    private fun String.intOrNot(): Boolean {
        val v = toIntOrNull()
        return when (v) {
            null -> false
            else -> true
        }
    }
}
