package com.dante.calorietracker.feature.age.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ageRoute = "age_route"

fun NavController.navigateToAge(navOptions: NavOptions? = null) =
    this.navigate(ageRoute, navOptions)

fun NavGraphBuilder.ageScreen() {
    composable(route = ageRoute) {
    }
}
