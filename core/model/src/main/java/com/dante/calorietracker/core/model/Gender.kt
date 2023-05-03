package com.dante.calorietracker.core.model

enum class Gender(val gender: String) {
    Male("male"),
    Female("female"),
    Other("other");

    companion object {
        fun getGenderFromString(gender: String): Gender {
            return when (gender) {
                Male.gender -> Male
                Female.gender -> Female
                else -> Other
            }
        }
    }
}
