package com.dante.calorietracker.core.model

sealed class MealType(val name: String) {
    object Breakfast : MealType("breakfast")
    object Lunch : MealType("lunch")
    object Dinner : MealType("dinner")
    object Snack : MealType("snack")
}

fun String?.asMealType(): MealType {
    return when (this) {
        MealType.Breakfast.name -> MealType.Breakfast
        MealType.Lunch.name -> MealType.Lunch
        MealType.Dinner.name -> MealType.Dinner
        MealType.Snack.name -> MealType.Snack
        else -> MealType.Breakfast
    }
}
