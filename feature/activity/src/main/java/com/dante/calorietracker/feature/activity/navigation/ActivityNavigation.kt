package com.dante.calorietracker.feature.activity.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val activityRoute = "activity_route"

fun NavController.navigateToActivity(navOptions: NavOptions? = null) =
    this.navigate(activityRoute, navOptions)

fun NavGraphBuilder.activityScreen(){
    composable(route = activityRoute){
    }
}
