package com.dante.calorietracker.core.model

enum class GoalType(val goal: String) {
    LoseWeight("lose_weight"),
    KeepWeight("keep_weight"),
    GainWeight("gain_weight");

    companion object {
        fun getGoalTypeFromString(goal: String): GoalType {
            return when (goal) {
                LoseWeight.goal -> LoseWeight
                GainWeight.goal -> GainWeight
                else -> KeepWeight
            }
        }
    }
}
