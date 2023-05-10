package com.dante.calorietracker.feature.age

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
class AgeViewModel @Inject constructor(
    private val repository: UserDataRepository,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    var shouldDisplayAgeNotFilled by mutableStateOf(false)
        private set

    val age = savedStateHandle.getStateFlow(AGE, "")

    fun onAgeEntered(age: String) {
        if (age.length <= 3) {
            savedStateHandle[AGE] = filterOutDigitsUseCase(age)
        }
    }

    fun onNextClick(action: () -> Unit) = viewModelScope.launch {
        val finalAge = age.value.toIntOrNull()
        if (finalAge != null) {
            shouldDisplayAgeNotFilled = false
            repository.saveAge(finalAge)
            action()
        } else {
            shouldDisplayAgeNotFilled = true
        }
    }

    fun clearAgeNotFilledState() {
        shouldDisplayAgeNotFilled = false
    }
}

private const val AGE = "age"
