package com.dante.calorietracker.feature.tracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.feature.tracker.TrackerOverviewRoute

const val trackerGraphRoutePattern = "tracker_graph"
const val trackerRoute = "tracker_route"

fun NavController.navigateToTracker(navOptions: NavOptions? = null) =
    this.navigate(trackerRoute, navOptions)

fun NavGraphBuilder.trackerGraph(
    onAddMealClick: (SearchArgs) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(route = trackerGraphRoutePattern, startDestination = trackerRoute) {
        composable(route = trackerRoute) {
            TrackerOverviewRoute(onAddMealClick)
        }
        nestedGraphs()
    }
}
