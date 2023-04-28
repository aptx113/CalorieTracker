package com.dante.calorietracker.feat.weight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val weightRoute = "weight_route"

fun NavController.navigateToWeight(navOptions: NavOptions? = null) =
    this.navigate(weightRoute, navOptions)
