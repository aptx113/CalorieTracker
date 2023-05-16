package com.dante.calorietracker.feature.nutrientGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.ui.utils.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val decimalValidatorUseCase: DecimalValidatorUseCase
) :
    ViewModel() {

    fun validatedValue(value: String, numberCount: Int, decimalCount: Int) =
        decimalValidatorUseCase(value, numberCount, decimalCount)

    fun onNextClick(
        carbsState: TextFieldState,
        proteinsState: TextFieldState,
        fatState: TextFieldState,
        action: () -> Unit
    ) = viewModelScope.launch {
        carbsState.displayErrors = !carbsState.isValid
        proteinsState.displayErrors = !proteinsState.isValid
        fatState.displayErrors = !fatState.isValid

        if (carbsState.isValid && proteinsState.isValid && fatState.isValid) {
            userDataRepository.apply {
                saveCarbRatio(carbsState.text.toFloat())
                saveProteinRatio(proteinsState.text.toFloat())
                saveFatRatio(fatState.text.toFloat())
            }
            action()
        }
    }
}