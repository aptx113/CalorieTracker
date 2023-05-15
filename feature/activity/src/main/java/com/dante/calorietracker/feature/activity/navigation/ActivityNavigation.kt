package com.dante.calorietracker.feature.activity.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.feature.activity.ActivityRoute

const val activityRoute = "activity_route"

fun NavController.navigateToActivity(navOptions: NavOptions? = null) =
    this.navigate(activityRoute, navOptions)

fun NavGraphBuilder.activityScreen(onNavigated: () -> Unit) {
    composable(route = activityRoute) {
        ActivityRoute(onNavigated = onNavigated)
    }
}
