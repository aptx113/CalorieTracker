package com.dante.calorietracker.feature.age.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.feature.age.AgeRoute

const val ageRoute = "age_route"

fun NavController.navigateToAge(navOptions: NavOptions? = null) =
    this.navigate(ageRoute, navOptions)

fun NavGraphBuilder.ageScreen(onNextClick: () -> Unit) {
    composable(route = ageRoute) {
        AgeRoute(onNavigated = onNextClick)
    }
}
