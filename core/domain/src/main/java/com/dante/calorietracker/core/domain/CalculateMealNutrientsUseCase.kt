package com.dante.calorietracker.core.domain

import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.model.UserInfo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.roundToInt

@ViewModelScoped
class CalculateMealNutrientsUseCase @Inject constructor(private val userDataRepository: UserDataRepository) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Flow<CalculateMealNutrientsResult> {
        val allNutrients = trackedFoods.groupBy { it.mealType }.mapValues { entry ->
            val type = entry.key
            val foods = entry.value
            MealNutrients(
                carbs = foods.sumOf { it.carbs },
                proteins = foods.sumOf { it.proteins },
                fats = foods.sumOf { it.fats },
                calories = foods.sumOf { it.calories },
                mealType = type
            )
        }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProteins = allNutrients.values.sumOf { it.proteins }
        val totalFats = allNutrients.values.sumOf { it.fats }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = userDataRepository.userInfo

        val result = userInfo.map {
            val caloriesGoal = calculateDailyCaloriesRequirement(it)
            val carbsGoal = (caloriesGoal * it.carbRatio / 4f).roundToInt()
            val proteinsGoal = (caloriesGoal * it.proteinRatio / 4f).roundToInt()
            val fatsGoal = (caloriesGoal * it.fatRatio / 9f).roundToInt()

            CalculateMealNutrientsResult(
                totalCarbs = totalCarbs,
                totalProteins = totalProteins,
                totalFats = totalFats,
                totalCalories = totalCalories,
                caloriesGoal = caloriesGoal,
                carbsGoal = carbsGoal,
                proteinsGoal = proteinsGoal,
                fatsGoal = fatsGoal,
                mealNutrients = allNutrients
            )
        }
        return result
    }

    private fun calculateBMR(userInfo: UserInfo): Int {
        val bmr = when (userInfo.gender) {
            is Gender.Male -> {
                (66.47 + 13.75 * userInfo.weight + 5 * userInfo.height - 6.75 * userInfo.age).roundToInt()
            }

            is Gender.Female -> {
                (655.09 + 9.56 * userInfo.weight + 1.84 * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
        return bmr
    }

    private fun calculateDailyCaloriesRequirement(userInfo: UserInfo): Int {
        val activityFactory = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }

        val caloriesExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (calculateBMR(userInfo) * activityFactory + caloriesExtra).roundToInt()
    }
}

data class MealNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int,
    val mealType: MealType
)

data class CalculateMealNutrientsResult(
    val carbsGoal: Int,
    val proteinsGoal: Int,
    val fatsGoal: Int,
    val caloriesGoal: Int,
    val totalCarbs: Int,
    val totalProteins: Int,
    val totalFats: Int,
    val totalCalories: Int,
    val mealNutrients: Map<MealType, MealNutrients>
)
