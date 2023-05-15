package com.dante.calorietracker.feature.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.ActivityLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val repository: UserDataRepository) :
    ViewModel() {

    var selectedActivity by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set

    fun onActivityLevelSelect(activityLevel: ActivityLevel) {
        selectedActivity = activityLevel
    }

    fun onNextClick(action: () -> Unit) = viewModelScope.launch {
        repository.saveActivityLevel(selectedActivity)
        action()
    }
}
