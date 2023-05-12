package com.dante.calorietracker.feature.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val repository: UserDataRepository,
    private val decimalValidatorUseCase: DecimalValidatorUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var shouldDisplayWeightNotFilled by mutableStateOf(false)
        private set

    val weight = savedStateHandle.getStateFlow(WEIGHT, "")

    fun onWeightEnter(weight: String) {
        if (weight.length <= 6) {
            savedStateHandle[WEIGHT] = decimalValidatorUseCase(weight, 3, 2)
        }
    }

    fun onNextClick(action: () -> Unit) = viewModelScope.launch {
        val finalWeight = weight.value.toFloatOrNull()
        if (finalWeight != null) {
            shouldDisplayWeightNotFilled = false
            repository.saveWeight(finalWeight)
            action()
        } else {
            shouldDisplayWeightNotFilled = true
        }
    }

    fun clearWeightNotFilledState() {
        shouldDisplayWeightNotFilled = false
    }
}

private const val WEIGHT = "weight"
