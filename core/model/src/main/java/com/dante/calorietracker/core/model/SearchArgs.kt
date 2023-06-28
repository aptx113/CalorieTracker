package com.dante.calorietracker.core.model

data class SearchArgs(
    val mealType: String,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int
)
