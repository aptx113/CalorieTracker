package com.dante.calorietracker.core.domain

import app.cash.turbine.test
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.model.UserInfo
import com.dante.calorietracker.core.model.asMealType
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class CalculateMealNutrientsUseCaseTest {

    private lateinit var calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        val repository = mockk<UserDataRepository>(relaxed = true)
        every { repository.userInfo } returns flowOf(
            UserInfo(
                gender = Gender.Male,
                age = 33,
                weight = 65f,
                height = 169,
                activityLevel = ActivityLevel.Medium,
                goalType = GoalType.KeepWeight,
                carbRatio = 0.4f,
                proteinRatio = 0.3f,
                fatRatio = 0.1f
            )
        )
        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(repository)
    }

    @Test
    fun calculateBreakfast_returnProperlyCaloriesResult() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = listOf("breakfast", "lunch", "dinner", "snack").random().asMealType(),
                imageUrl = null,
                amount = 100,
                date = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods).map {
            it.first.mealNutrients.values
                .filter { mealNutrients -> mealNutrients.mealType is MealType.Breakfast }
                .sumOf { mealNutrients -> mealNutrients.calories }
        }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Breakfast }.sumOf { it.calories }

        result.test {
            assertEquals(expectedCalories, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun calculateLunch_returnProperlyCaloriesResult() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = listOf("breakfast", "lunch", "dinner", "snack").random().asMealType(),
                imageUrl = null,
                amount = 100,
                date = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods).map {
            it.first.mealNutrients.values
                .filter { mealNutrients -> mealNutrients.mealType is MealType.Lunch }
                .sumOf { mealNutrients -> mealNutrients.calories }
        }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Lunch }.sumOf { it.calories }

        result.test {
            assertEquals(expectedCalories, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun calculateDinner_returnProperlyCaloriesResult() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = listOf("breakfast", "lunch", "dinner", "snack").random().asMealType(),
                imageUrl = null,
                amount = 100,
                date = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods).map {
            it.first.mealNutrients.values
                .filter { mealNutrients -> mealNutrients.mealType is MealType.Dinner }
                .sumOf { mealNutrients -> mealNutrients.calories }
        }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Dinner }.sumOf { it.calories }

        result.test {
            assertEquals(expectedCalories, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun calculateSnack_returnProperlyCaloriesResult() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = listOf("breakfast", "lunch", "dinner", "snack").random().asMealType(),
                imageUrl = null,
                amount = 100,
                date = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods).map {
            it.first.mealNutrients.values
                .filter { mealNutrients -> mealNutrients.mealType is MealType.Snack }
                .sumOf { mealNutrients -> mealNutrients.calories }
        }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Snack }.sumOf { it.calories }

        result.test {
            assertEquals(expectedCalories, awaitItem())
            awaitComplete()
        }
    }
}
