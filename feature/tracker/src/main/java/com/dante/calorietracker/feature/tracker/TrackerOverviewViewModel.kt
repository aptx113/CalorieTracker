package com.dante.calorietracker.feature.tracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.domain.CalculateMealNutrientsUseCase
import com.dante.calorietracker.core.domain.DeleteTrackedFoodUseCase
import com.dante.calorietracker.core.domain.GetFoodsForDateUseCase
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.feature.tracker.model.Meal
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    private val getFoodsForDateUseCase: GetFoodsForDateUseCase,
    private val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase
) :
    ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private var getFoodsForDateJob: Job? = null

    fun onAddFoodClick(meal: Meal, searchAction: (SearchArgs) -> Unit) {
        searchAction(
            SearchArgs(
                mealType = meal.mealType.name,
                dayOfMonth = state.date.dayOfMonth,
                month = state.date.month.value,
                year = state.date.year
            )
        )
    }

    fun onDeleteTrackedFoodClick(trackedFood: TrackedFood) = viewModelScope.launch {
        deleteTrackedFoodUseCase(trackedFood)
        refreshFoods()
    }

    fun onNextDayClick() {
        state = state.copy(date = state.date.plus(1, DateTimeUnit.DAY))
        refreshFoods()
    }

    fun onPreviousDayClick() {
        state = state.copy(date = state.date.minus(1, DateTimeUnit.DAY))
        refreshFoods()
    }

    fun onToggleMealClick(meal: Meal) {
        state = state.copy(
            meals = state.meals.map {
                if (it.name == meal.name) {
                    it.copy(isExpanded = !it.isExpanded)
                } else it
            }
        )
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = viewModelScope.launch {
            getFoodsForDateUseCase(state.date).flatMapConcat { foods ->
                calculateMealNutrientsUseCase(foods)
            }.collect {
                val (result, foods) = it
                val meals = state.meals.map { meal ->
                    val nutrientsForMeal =
                        result.mealNutrients[meal.mealType] ?: return@map meal.copy(
                            carbs = 0,
                            protein = 0,
                            fat = 0,
                            calories = 0
                        )
                    meal.copy(
                        carbs = nutrientsForMeal.carbs,
                        protein = nutrientsForMeal.protein,
                        fat = nutrientsForMeal.fat,
                        calories = nutrientsForMeal.calories
                    )
                }
                state = state.copy(
                    totalCarbs = result.totalCarbs,
                    totalProtein = result.totalProtein,
                    totalFat = result.totalFat,
                    totalCalories = result.totalCalories,
                    carbsGoal = result.carbsGoal,
                    proteinGoal = result.proteinGoal,
                    fatGoal = result.fatGoal,
                    caloriesGoal = result.caloriesGoal,
                    trackedFoods = foods,
                    meals = meals
                )
            }
        }
    }
}
