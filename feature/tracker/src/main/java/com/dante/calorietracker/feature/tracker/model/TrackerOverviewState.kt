package com.dante.calorietracker.feature.tracker.model

import com.dante.calorietracker.core.model.TrackedFood
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class TrackerOverviewState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbsGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val trackedFoods: List<TrackedFood> = emptyList(),
    val meals: List<Meal> = defaultMeals
)
