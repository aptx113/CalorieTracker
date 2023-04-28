package com.dante.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dante.calorietracker.ui.CalorieTrackerAppState

@Composable
fun CalorieTrackerNavHost(
    appState: CalorieTrackerAppState,
    modifier: Modifier = Modifier,
    startDestination: String = ""
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

    }
}
