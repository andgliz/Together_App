package com.example.livingtogether.ui.housework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.domain.repository.AuthRepository
import com.example.livingtogether.domain.repository.HouseworkRepository
import com.example.livingtogether.domain.repository.UserRepository
import com.example.livingtogether.ui.HouseworkUiState
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.toHousework
import com.example.livingtogether.ui.toHouseworkViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HouseworkViewModel(
    private val houseworkRepository: HouseworkRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private var currentUserFamily = ""

    private val _uiState: MutableStateFlow<HouseworkUiState> = MutableStateFlow(HouseworkUiState())
    val uiState: StateFlow<HouseworkUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            currentUserFamily =
                userRepository.getUser(authRepository.currentUser!!.uid)!!.family
            initializeUiState(currentUserFamily)
        }
    }

    private fun initializeUiState(currentUserFamily: String) {
        viewModelScope.launch {
            houseworkRepository.getHouseworkListFlow(currentUserFamily).collect { housework ->
                _uiState.value =
                    HouseworkUiState(
                        houseworkList = housework.map { it.toHouseworkViewData() }
                            .sortedBy { it.cost }
                    )
            }
        }
    }

    fun updateUiState(housework: HouseworkViewData) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                houseworkInput = housework,
                isEnabled = isValidInput(housework)
            )
        }
    }

    fun onAddHouseworkClicked() {
        viewModelScope.launch {
            houseworkRepository.createHousework(
                _uiState.value.houseworkInput.toHousework(
                    currentUserFamily
                )
            )
        }
    }

    fun onDeleteHouseworkClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            houseworkRepository.deleteHousework(housework.toHousework(currentUserFamily).id)
        }
    }

    fun onHouseworkClicked() {
        viewModelScope.launch {
            houseworkRepository.updateHousework(
                _uiState.value.houseworkInput.toHousework(
                    currentUserFamily
                )
            )
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
