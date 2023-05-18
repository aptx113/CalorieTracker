package com.dante.calorietracker.feature.nutrientGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.ui.state.TextFieldState
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
        proteinState: TextFieldState,
        fatState: TextFieldState,
        action: () -> Unit
    ) = viewModelScope.launch {
        carbsState.displayErrors = !carbsState.isValid
        proteinState.displayErrors = !proteinState.isValid
        fatState.displayErrors = !fatState.isValid

        if (carbsState.isValid && proteinState.isValid && fatState.isValid) {
            userDataRepository.apply {
                saveCarbRatio(carbsState.text.toFloat())
                saveProteinRatio(proteinState.text.toFloat())
                saveFatRatio(fatState.text.toFloat())
            }
            action()
        }
    }
}
