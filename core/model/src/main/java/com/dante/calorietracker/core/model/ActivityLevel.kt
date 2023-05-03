package com.dante.calorietracker.core.model

enum class ActivityLevel(val level: String) {
    Low("low"),
    Medium("medium"),
    High("high");

    companion object {
        fun getActivityLevelFromString(level: String): ActivityLevel {
            return when (level) {
                Low.level -> Low
                Medium.level -> Medium
                else -> High
            }
        }
    }
}
