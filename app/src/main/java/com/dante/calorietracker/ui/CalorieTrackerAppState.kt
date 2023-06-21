package com.dante.calorietracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.feature.tracker.navigation.trackerRoute
import com.dante.calorietracker.feature.welcome.navigation.welcomeRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberCalorieTrackerAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    userDataRepository: UserDataRepository
): CalorieTrackerAppState {
    return remember(navController, coroutineScope, userDataRepository) {
        CalorieTrackerAppState(navController, coroutineScope, userDataRepository)
    }
}

@Stable
class CalorieTrackerAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    userDataRepository: UserDataRepository
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestinationRoute: StateFlow<String> = userDataRepository.userInfo.map {
        if (it.carbRatio != 0f && it.proteinRatio != 0f && it.fatRatio != 0f) {
            trackerRoute
        } else {
            welcomeRoute
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = welcomeRoute
    )
}
