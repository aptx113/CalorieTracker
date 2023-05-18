package com.dante.calorietracker.core.data.repository

import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TrackerRepository {
    fun searchFood(query: String, page: Int, pageSize: Int): Flow<List<TrackableFood>>

    suspend fun insertTrackedFood(trackedFood: TrackedFood)

    suspend fun deleteTrackedFood(trackedFood: TrackedFood)

    fun getFoodForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}
