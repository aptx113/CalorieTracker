package com.dante.calorietracker.core.model

import kotlinx.datetime.LocalDate

data class TrackedFood(
    val name: String,
    val carbs: Int,
    val proteins: Int,
    val fats: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int,
    val id: Int? = null
)
