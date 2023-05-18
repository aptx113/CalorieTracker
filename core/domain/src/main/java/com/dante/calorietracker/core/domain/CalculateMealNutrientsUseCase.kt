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
                protein = foods.sumOf { it.protein },
                fat = foods.sumOf { it.fat },
                calorie = foods.sumOf { it.calorie },
                mealType = type
            )
        }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalorie = allNutrients.values.sumOf { it.calorie }

        val userInfo = userDataRepository.userInfo

        val result = userInfo.map {
            val calorieGoal = calculateDailyCaloriesRequirement(it)
            val carbsGoal = (calorieGoal * it.carbRatio / 4f).roundToInt()
            val proteinGoal = (calorieGoal * it.proteinRatio / 4f).roundToInt()
            val fatGoal = (calorieGoal * it.fatRatio / 9f).roundToInt()

            CalculateMealNutrientsResult(
                totalCarbs = totalCarbs,
                totalProtein = totalProtein,
                totalFat = totalFat,
                totalCalorie = totalCalorie,
                caloriesGoal = calorieGoal,
                carbsGoal = carbsGoal,
                proteinGoal = proteinGoal,
                fatGoal = fatGoal,
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
    val calorie: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val mealType: MealType
)

data class CalculateMealNutrientsResult(
    val carbsGoal: Int,
    val proteinGoal: Int,
    val fatGoal: Int,
    val caloriesGoal: Int,
    val totalCarbs: Int,
    val totalProtein: Int,
    val totalFat: Int,
    val totalCalorie: Int,
    val mealNutrients: Map<MealType, MealNutrients>
)
