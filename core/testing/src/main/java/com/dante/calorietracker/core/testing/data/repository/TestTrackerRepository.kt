package com.dante.calorietracker.core.testing.data.repository

import com.dante.calorietracker.core.data.repository.TrackerRepository
import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class TestTrackerRepository : TrackerRepository {

    private val listOfTrackedFood = mutableListOf<TrackedFood>()

    override fun searchFood(query: String, page: Int, pageSize: Int): Flow<List<TrackableFood>> {
        val listOfTrackableFood = (1..10).map {
            TrackableFood(
                name = "name",
                imageUrl = null,
                caloriePer100g = Random.nextInt(100),
                carbsPer100g = Random.nextInt(100),
                proteinPer100g = Random.nextInt(100),
                fatPer100g = Random.nextInt(100)
            )
        }
        return flowOf(listOfTrackableFood)
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        listOfTrackedFood.add(trackedFood.copy(id = Random.nextInt()))
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        listOfTrackedFood.remove(trackedFood)
    }

    override fun getFoodForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return flowOf(listOfTrackedFood)
    }
}
