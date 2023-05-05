package com.dante.calorietracker.core.model

sealed class Gender(val gender: String) {
    object Male : Gender("Male")
    object Female : Gender("Female")
    object Other : Gender("Other")

    companion object {
        fun String.getGenderFromString(): Gender {
            return when (this) {
                Male.gender -> Male
                Female.gender -> Female
                else -> Other
            }
        }
    }
}
