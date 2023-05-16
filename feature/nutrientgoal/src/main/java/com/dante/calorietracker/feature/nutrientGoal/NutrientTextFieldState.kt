package com.dante.calorietracker.feature.nutrientGoal

import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.state.TextFieldState
import com.dante.calorietracker.core.ui.state.textFieldStateSaver

enum class Nutrient {
    Carbs,
    Proteins,
    Fats
}

class NutrientGoalState(nutrient: Nutrient? = null) :
    TextFieldState(validator = ::isNutrientGoalValid, errorResFor = { nutrientGoalError(nutrient) })

private fun isNutrientGoalValid(nutrientGoal: String): Boolean {
    return nutrientGoal.isNotEmpty()
}

private fun nutrientGoalError(nutrient: Nutrient?): Int {
    return when (nutrient) {
        Nutrient.Carbs -> R.string.error_carbs_cant_be_empty
        Nutrient.Proteins -> R.string.error_proteins_cant_be_empty
        Nutrient.Fats -> R.string.error_fat_cant_be_empty
        else -> 0
    }
}

val NutrientStateSaver = textFieldStateSaver(NutrientGoalState())
