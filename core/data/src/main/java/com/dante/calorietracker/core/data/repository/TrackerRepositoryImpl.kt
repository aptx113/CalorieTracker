package com.dante.calorietracker.core.data.repository

import com.dante.calorietracker.core.data.mapper.asTrackableFood
import com.dante.calorietracker.core.data.mapper.asTrackedFood
import com.dante.calorietracker.core.data.mapper.asTrackedFoodEntity
import com.dante.calorietracker.core.database.CalorieTrackerDao
import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.network.CalorieTrackerDataSource
import com.dante.calorietracker.core.network.CalorieTrackerDispatcher.IO
import com.dante.calorietracker.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: CalorieTrackerDataSource,
    private val dao: CalorieTrackerDao
) :
    TrackerRepository {
    override fun searchFood(query: String, page: Int, pageSize: Int): Flow<List<TrackableFood>> =
        flow {
            dataSource.searchFood(query, page, pageSize).map {
                it.asTrackableFood()
            }.also { emit(it) }
        }.flowOn(ioDispatcher)

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        withContext(ioDispatcher) {
            dao.insertTrackedFood(trackedFood.asTrackedFoodEntity())
        }
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        withContext(ioDispatcher) {
            dao.deleteTrackedFood(trackedFood.asTrackedFoodEntity())
        }
    }

    override fun getFoodForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(localDate.dayOfMonth, localDate.month.value, localDate.year)
            .map { it.map { food -> food.asTrackedFood() } }
    }
}
