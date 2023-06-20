package com.dante.calorietracker.feature.tracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dante.calorietracker.feature.tracker.TrackerOverviewRoute

const val trackedFoodSearchQuery = "trackedFoodSearchQuery"
const val trackerRoute = "tracker_route/{$trackedFoodSearchQuery}"

fun NavController.navigateToTracker(navOptions: NavOptions? = null) =
    this.navigate(trackerRoute, navOptions)

fun NavGraphBuilder.trackerScreen(onNavigated: (String) -> Unit) {
    composable(
        route = trackerRoute,
        arguments = listOf(navArgument(trackedFoodSearchQuery) { type = NavType.StringType })
    ) {
        TrackerOverviewRoute(onNavigated = onNavigated)
    }
}
