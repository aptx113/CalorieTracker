package com.dante.calorietracker.feature.tracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.feature.tracker.TrackerOverviewRoute

const val trackerRoute = "tracker_route"

fun NavController.navigateToTracker(navOptions: NavOptions? = null) =
    this.navigate(trackerRoute, navOptions)

fun NavGraphBuilder.trackerScreen(onNavigated: (SearchArgs) -> Unit) {
    composable(route = trackerRoute) {
        TrackerOverviewRoute(onNavigated = onNavigated)
    }
}
