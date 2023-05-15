package com.dante.calorietracker.feature.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.GoalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository: UserDataRepository) : ViewModel() {

    var selectedGoal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    fun onGoalSelect(goalType: GoalType) {
        selectedGoal = goalType
    }

    fun onNextClick(action: () -> Unit) = viewModelScope.launch {
        repository.saveGoalType(selectedGoal)
        action()
    }
}
