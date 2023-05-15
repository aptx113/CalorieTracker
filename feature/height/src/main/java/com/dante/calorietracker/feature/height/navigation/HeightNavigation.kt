package com.dante.calorietracker.feature.height.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.feature.height.HeightRoute

const val heightRoute = "height_route"

fun NavController.navigateToHeight(navOptions: NavOptions? = null) =
    this.navigate(heightRoute, navOptions)

fun NavGraphBuilder.heightScreen(onNavigated: () -> Unit) {
    composable(route = heightRoute) {
        HeightRoute(onNavigated = onNavigated)
    }
}
