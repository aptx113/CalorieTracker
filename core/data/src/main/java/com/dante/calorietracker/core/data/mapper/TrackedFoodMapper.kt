package com.dante.calorietracker.core.data.mapper

import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.model.asMealType
import kotlinx.datetime.LocalDate

fun TrackedFoodEntity.asTrackedFood(): TrackedFood = TrackedFood(
    name = name,
    carbs = carbs,
    protein = proteins,
    fat = fats,
    imageUrl = imageUrl,
    mealType = type.asMealType(),
    amount = amount,
    date = LocalDate(year, month, dayOfMonth),
    calories = calories,
    id = id
)

fun TrackedFood.asTrackedFoodEntity(): TrackedFoodEntity = TrackedFoodEntity(
    name = name,
    carbs = carbs,
    proteins = protein,
    fats = fat,
    imageUrl = imageUrl,
    type = mealType.name,
    amount = amount,
    dayOfMonth = date.dayOfMonth,
    month = date.monthNumber,
    year = date.year,
    calories = calories,
    id = id
)
