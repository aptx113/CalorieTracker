package com.dante.calorietracker.core.data.testdoubles

import com.dante.calorietracker.core.database.CalorieTrackerDao
import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestTrackerDao : CalorieTrackerDao {

    private var entitiesStateFlow = MutableStateFlow(emptyList<TrackedFoodEntity>())

    override suspend fun insertTrackedFood(trackedFood: TrackedFoodEntity) {
        entitiesStateFlow.update { oldValues -> (oldValues + trackedFood).distinctBy { it.id } }
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFoodEntity) {
        entitiesStateFlow.update { list ->
            list.filterNot { it.id == trackedFood.id }
        }
    }

    override fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>> =
        entitiesStateFlow.map { list ->
            list.filter { it.dayOfMonth == day && it.month == month && it.year == year }
        }
}
