package com.dante.calorietracker.core.model

sealed class GoalType(val goal: String) {
    object LoseWeight : GoalType("lose_weight")
    object KeepWeight : GoalType("keep_weight")
    object GainWeight : GoalType("gain_weight");

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
