package com.dante.calorietracker.feature.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val goalRoute = "goal_route"

fun NavController.navigateToGoal(navOptions: NavOptions? = null) =
    this.navigate(goalRoute, navOptions)

fun NavGraphBuilder.goalScreen() {
    composable(route = goalRoute) {
    }
}
