package com.example.livingtogether.ui.housework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livingtogether.data.TogetherRepository
import com.example.livingtogether.ui.HouseworkViewData
import com.example.livingtogether.ui.HouseworkUiState
import com.example.livingtogether.ui.toHousework
import com.example.livingtogether.ui.toHouseworkViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HouseworkViewModel(
    private val togetherRepository: TogetherRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HouseworkUiState> = MutableStateFlow(HouseworkUiState())
    val uiState: StateFlow<HouseworkUiState> = _uiState.asStateFlow()

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.value =
                HouseworkUiState(
                    houseworkList = togetherRepository.getHouseworkStream()
                        .map { it.toHouseworkViewData() }
                )
        }
    }

    fun updateUiState(housework: HouseworkViewData) {
        viewModelScope.launch {
            _uiState.value = HouseworkUiState(
                houseworkInput = housework,
                isEnabled = isValidInput(housework),
                houseworkList = togetherRepository.getHouseworkStream()
                    .map { it.toHouseworkViewData() }
            )
        }

    }

    fun onAddHouseworkClicked() {
        viewModelScope.launch {
            togetherRepository.insertIntoHouseworkStream(_uiState.value.houseworkInput.toHousework())
            initializeUiState()
        }
    }

    fun onDeleteHouseworkClicked(housework: HouseworkViewData) {
        viewModelScope.launch {
            togetherRepository.deleteFromHouseworkStream(housework.toHousework())
            initializeUiState()
        }
    }

    fun onHouseworkClicked() {
        viewModelScope.launch {
            togetherRepository.updateHouseworkStream(_uiState.value.houseworkInput.toHousework())
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
        return when(v) {
            null -> false
            else -> true
        }
    }
}
