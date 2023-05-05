package com.dante.calorietracker.core.model

sealed class ActivityLevel(val level: String) {
    object Low : ActivityLevel("Low")
    object Medium : ActivityLevel("Medium")
    object High : ActivityLevel("High");

    companion object {
        fun getActivityLevelFromString(level: String): ActivityLevel {
            return when (level) {
                High.level -> High
                Medium.level -> Medium
                else -> Low
            }
        }
    }
}
