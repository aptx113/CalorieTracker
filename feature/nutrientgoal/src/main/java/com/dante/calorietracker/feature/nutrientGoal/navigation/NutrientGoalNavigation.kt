package com.dante.calorietracker.feature.nutrientGoal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.feature.nutrientGoal.NutrientGoalRoute

const val nutrientGoalRoute = "nutrient_goal_route"

fun NavController.navigateToNutrientGoal(navOptions: NavOptions? = null) =
    this.navigate(nutrientGoalRoute)

fun NavGraphBuilder.nutrientGoalScreen(onNavigated: () -> Unit) {
    composable(route = nutrientGoalRoute) {
        NutrientGoalRoute(onNavigated = onNavigated)
    }
}
