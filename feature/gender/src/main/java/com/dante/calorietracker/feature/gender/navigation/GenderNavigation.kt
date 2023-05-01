package com.dante.calorietracker.feature.gender.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val genderRoute = "gender_route"

fun NavController.navigateToGender(navOptions: NavOptions? = null) =
    this.navigate(genderRoute, navOptions)

fun NavGraphBuilder.genderScreen() {
    composable(route = genderRoute) {
    }
}
