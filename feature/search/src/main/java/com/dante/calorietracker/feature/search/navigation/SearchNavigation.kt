package com.dante.calorietracker.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dante.calorietracker.core.model.SearchArgs

const val mealType = "mealType"
const val dayOfMonth = "day"
const val month = "month"
const val year = "year"
const val searchRoute = "search_route"


fun NavController.navigateToSearch(searchArg: SearchArgs, navOptions: NavOptions? = null) =
    this.navigate(
        "$searchRoute/${searchArg.mealType}/${searchArg.dayOfMonth}/${searchArg.month}/${searchArg.year}",
        navOptions
    )

fun NavGraphBuilder.searchScreen(onBackClick: () -> Unit) {
    composable(
        route = "$searchRoute/{$mealType}/{$dayOfMonth}/{$month}/{$year}",
        arguments = listOf(
            navArgument(mealType) { type = NavType.StringType },
            navArgument(dayOfMonth) { type = NavType.IntType },
            navArgument(month) { type = NavType.IntType },
            navArgument(year) { type = NavType.IntType }
        )
    ) {
    }
}
