package com.dante.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dante.calorietracker.feature.welcome.navigation.welcomeRoute
import com.dante.calorietracker.feature.welcome.navigation.welcomeScreen
import com.dante.calorietracker.ui.CalorieTrackerAppState

@Composable
fun CalorieTrackerNavHost(
    appState: CalorieTrackerAppState,
    modifier: Modifier = Modifier,
    startDestination: String = welcomeRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        welcomeScreen()
    }
}
