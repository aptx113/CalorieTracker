package com.dante.calorietracker.feature.tracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val trackerRoute = "tracker_route"

fun NavController.navigateToTracker(navOptions: NavOptions? = null) =
    this.navigate(trackerRoute, navOptions)

fun NavGraphBuilder.trackerScreen() {
    composable(route = trackerRoute) {
    }
}
