package com.dante.calorietracker.core.model

enum class ActivityLevel(val level: String) {
    Low("Low"),
    Medium("Medium"),
    High("High");

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
