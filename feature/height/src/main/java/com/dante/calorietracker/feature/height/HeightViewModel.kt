package com.dante.calorietracker.feature.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val repository: UserDataRepository,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var shouldDisplayHeightNotFilled by mutableStateOf(false)
        private set

    val height = savedStateHandle.getStateFlow(HEIGHT, "")

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            savedStateHandle[HEIGHT] = filterOutDigitsUseCase(height)
        }
    }

    fun onNextClick(onNavigated: () -> Unit) = viewModelScope.launch {
        val finalHeight = height.value.toIntOrNull()
        if (finalHeight != null) {
            shouldDisplayHeightNotFilled = false
            repository.saveHeight(finalHeight)
            onNavigated()
        } else {
            shouldDisplayHeightNotFilled = true
        }
    }

    fun clearHeightNotFilledState() {
        shouldDisplayHeightNotFilled = false
    }
}

private const val HEIGHT = "height"
