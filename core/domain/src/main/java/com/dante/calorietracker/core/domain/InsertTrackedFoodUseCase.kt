package com.dante.calorietracker.core.domain

import com.dante.calorietracker.core.data.repository.TrackerRepository
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.model.TrackedFood
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.datetime.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

@ViewModelScoped
class InsertTrackedFoodUseCase @Inject constructor(private val repository: TrackerRepository) {
    suspend operator fun invoke(
        trackableFood: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        val food = TrackedFood(
            name = trackableFood.name,
            carbs = ((trackableFood.carbsPer100g / 100f) * amount).roundToInt(),
            protein = ((trackableFood.proteinPer100g / 100f) * amount).roundToInt(),
            fat = ((trackableFood.fatPer100g / 100f) * amount).roundToInt(),
            calories = ((trackableFood.caloriePer100g / 100f) * amount).roundToInt(),
            imageUrl = trackableFood.imageUrl,
            amount = amount,
            mealType = mealType,
            date = date
        )
        repository.insertTrackedFood(food)
    }
}
