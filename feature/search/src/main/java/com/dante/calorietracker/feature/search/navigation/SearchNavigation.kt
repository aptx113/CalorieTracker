package com.dante.calorietracker.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.feature.search.SearchRoute

const val mealType = "mealType"
const val dayOfMonth = "day"
const val month = "month"
const val year = "year"
const val searchRoute = "search_route"
const val searchArgs = "searchArgs"


fun NavController.navigateToSearch(searchArgs: SearchArgs, navOptions: NavOptions? = null) =
    this.navigate(
        "$searchRoute/${searchArgs.mealType}/${searchArgs.dayOfMonth}/${searchArgs.month}/${searchArgs.year}",
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
        val mealType = checkNotNull(it.arguments?.getString(mealType))
        val dayOfMonth = checkNotNull(it.arguments?.getInt(dayOfMonth))
        val month = checkNotNull(it.arguments?.getInt(month))
        val year = checkNotNull(it.arguments?.getInt(year))
        SearchRoute(
            searchArgs = SearchArgs(mealType, dayOfMonth, month, year),
            onBackClick = onBackClick
        )
    }
}
