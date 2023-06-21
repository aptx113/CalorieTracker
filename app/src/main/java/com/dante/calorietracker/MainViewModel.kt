package com.dante.calorietracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.feature.tracker.navigation.trackerRoute
import com.dante.calorietracker.feature.welcome.navigation.welcomeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(userDataRepository: UserDataRepository) : ViewModel() {
    val uiState: StateFlow<MainUiState> = userDataRepository.userInfo.map {
        val destination = if (it.carbRatio != 0f && it.proteinRatio != 0f && it.fatRatio != 0f) {
            trackerRoute
        } else {
            welcomeRoute
        }
        MainUiState.StartDestination(destination)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MainUiState.Loading)
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class StartDestination(val destination: String) : MainUiState
}
