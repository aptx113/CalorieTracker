package com.dante.calorietracker.feature.weight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val weightRoute = "weight_route"

fun NavController.navigateToWeight(navOptions: NavOptions? = null) =
    this.navigate(weightRoute, navOptions)

fun NavGraphBuilder.weightScreen() {
    composable(route = weightRoute) {
    }
}
