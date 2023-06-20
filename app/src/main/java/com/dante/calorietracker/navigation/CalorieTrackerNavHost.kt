package com.dante.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dante.calorietracker.feature.activity.navigation.activityScreen
import com.dante.calorietracker.feature.activity.navigation.navigateToActivity
import com.dante.calorietracker.feature.age.navigation.ageScreen
import com.dante.calorietracker.feature.age.navigation.navigateToAge
import com.dante.calorietracker.feature.gender.navigation.genderScreen
import com.dante.calorietracker.feature.gender.navigation.navigateToGender
import com.dante.calorietracker.feature.goal.navigation.goalScreen
import com.dante.calorietracker.feature.goal.navigation.navigateToGoal
import com.dante.calorietracker.feature.height.navigation.heightScreen
import com.dante.calorietracker.feature.height.navigation.navigateToHeight
import com.dante.calorietracker.feature.nutrientGoal.navigation.navigateToNutrientGoal
import com.dante.calorietracker.feature.nutrientGoal.navigation.nutrientGoalScreen
import com.dante.calorietracker.feature.search.navigation.navigateToSearch
import com.dante.calorietracker.feature.search.navigation.searchScreen
import com.dante.calorietracker.feature.tracker.navigation.navigateToTracker
import com.dante.calorietracker.feature.tracker.navigation.trackerScreen
import com.dante.calorietracker.feature.weight.navigation.navigateToWeight
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
        welcomeScreen { navController.navigateToGender() }
        activityScreen { navController.navigateToGoal() }
        ageScreen(onNextClick = { navController.navigateToHeight() })
        genderScreen(onNextClick = { navController.navigateToAge() })
        goalScreen(onNavigated = { navController.navigateToNutrientGoal() })
        heightScreen { navController.navigateToWeight() }
        nutrientGoalScreen { navController.navigateToTracker() }
        searchScreen()
        trackerScreen(onNavigated = {navController.navigateToSearch()})
        weightScreen(onNavigated = { navController.navigateToActivity() })
    }
}
