package com.dante.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dante.calorietracker.feature.activity.navigation.activityScreen
import com.dante.calorietracker.feature.age.navigation.ageScreen
import com.dante.calorietracker.feature.age.navigation.navigateToAge
import com.dante.calorietracker.feature.gender.navigation.genderScreen
import com.dante.calorietracker.feature.goal.navigation.goalScreen
import com.dante.calorietracker.feature.height.navigation.heightScreen
import com.dante.calorietracker.feature.nutrientGoal.navigation.nutrientGoalScreen
import com.dante.calorietracker.feature.search.navigation.searchScreen
import com.dante.calorietracker.feature.tracker.navigation.trackerScreen
import com.dante.calorietracker.feature.weight.navigation.weightScreen
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
        welcomeScreen(onAgeNavigated = { navController.navigateToAge() })
        activityScreen()
        ageScreen()
        genderScreen()
        goalScreen()
        heightScreen()
        nutrientGoalScreen()
        searchScreen()
        trackerScreen()
        weightScreen()
    }
}