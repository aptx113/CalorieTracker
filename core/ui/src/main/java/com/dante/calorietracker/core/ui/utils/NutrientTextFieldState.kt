package com.dante.calorietracker.core.ui.utils

import com.dante.calorietracker.core.ui.R

enum class Nutrient {
    Carbs,
    Protein,
    Fat
}

class NutrientGoalState(nutrient: Nutrient? = null) :
    TextFieldState(validator = ::isNutrientGoalValid, errorResFor = { nutrientGoalError(nutrient) })

private fun isNutrientGoalValid(nutrientGoal: String): Boolean {
    return nutrientGoal.isNotEmpty()
}

private fun nutrientGoalError(nutrient: Nutrient?): Int {
    return when (nutrient) {
        Nutrient.Carbs -> R.string.error_carbs_cant_be_empty
        Nutrient.Protein -> R.string.error_proteins_cant_be_empty
        Nutrient.Fat -> R.string.error_fat_cant_be_empty
        else -> 0
    }
}

val NutrientStateSaver = textFieldStateSaver(NutrientGoalState())
