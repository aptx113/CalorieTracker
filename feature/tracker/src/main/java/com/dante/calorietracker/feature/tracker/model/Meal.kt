package com.dante.calorietracker.feature.tracker.model

import androidx.annotation.DrawableRes
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.ui.R

data class Meal(
    val name: String,
    @DrawableRes
    val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    Meal(
        name = MealType.Breakfast.name,
        drawableRes = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast
    ),
    Meal(
        name = MealType.Lunch.name,
        drawableRes = R.drawable.ic_lunch,
        mealType = MealType.Lunch
    ),
    Meal(
        name = MealType.Dinner.name,
        drawableRes = R.drawable.ic_dinner,
        mealType = MealType.Dinner
    ),
    Meal(
        name = MealType.Snack.name,
        drawableRes = R.drawable.ic_snack,
        mealType = MealType.Snack
    ),
)
