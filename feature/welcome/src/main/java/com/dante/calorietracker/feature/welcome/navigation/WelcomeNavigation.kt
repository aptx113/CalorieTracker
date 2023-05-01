package com.dante.calorietracker.feature.welcome.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dante.calorietracker.feature.welcome.WelcomeRoute

const val welcomeRoute = "welcome_route"

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) =
    this.navigate(welcomeRoute, navOptions)

fun NavGraphBuilder.welcomeScreen(onAgeNavigated: () -> Unit) {
    composable(route = welcomeRoute) {
        WelcomeRoute(onAgeNavigated)
    }
}
