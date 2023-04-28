package com.dante.calorietracker.feature.tracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val trackerRoute = "tracker_route"

fun NavController.navigateToTracker(navOptions: NavOptions? = null) =
    this.navigate(trackerRoute, navOptions)
