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
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.feature.tracker.model.Meal
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
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

    fun onAddFoodClick(meal: Meal, searchAction: (String) -> Unit) {
        searchAction("${meal.mealType.name}/${state.date.dayOfMonth}/${state.date.month}/${state.date.year}")
    }

    fun onDeleteTrackedFoodClick(trackedFood: TrackedFood) = viewModelScope.launch {
        deleteTrackedFoodUseCase(trackedFood)
    }

    fun onNextDayClick() {}
    fun onPreviousDayClick() {}
    fun onToggleMealClick() {}

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = viewModelScope.launch {
            getFoodsForDateUseCase(state.date).flatMapConcat { foods ->
                calculateMealNutrientsUseCase(foods)
            }.collect {

            }
            //            collect{
//                foods -> val nutrientsResult = calculateMealNutrientsUseCase(foods)
//                state = state.copy(totalCarbs = nutrientsResult.to)
//            }
        }
    }
}
