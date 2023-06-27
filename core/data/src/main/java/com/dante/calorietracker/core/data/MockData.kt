package com.dante.calorietracker.core.data

import com.dante.calorietracker.core.model.TrackableFood

val mockTrackableFood by lazy {
    TrackableFood(
        name = "pasta",
        imageUrl = "https://www.lemonblossoms.com/wp-content/uploads/2022/03/Butter-Noodles-S5.jpg",
        caloriePer100g = 300,
        carbsPer100g = 40,
        proteinPer100g = 10,
        fatPer100g = 10
    )
}
