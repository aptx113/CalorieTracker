package com.dante.calorietracker.core.model

enum class Gender(val gender: String) {
    Male("Male"),
    Female("Female"),
    Other("Other");

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
