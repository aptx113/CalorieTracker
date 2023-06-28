package com.dante.calorietracker.feature.search

import com.dante.calorietracker.core.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)