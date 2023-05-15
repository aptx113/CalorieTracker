package com.dante.calorietracker.feature.nutrientGoal.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor() : ViewModel() {

}

private const val carbs = "carbs_ratio"

private const val protein = "protein_ratio"

private const val fat = "fat_ratio"
