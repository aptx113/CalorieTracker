package com.dante.calorietracker.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalorieTrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFood: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFood: TrackedFoodEntity)

    @Query("SELECT * FROM tracked_food WHERE dayOfMonth = :day AND month = :month AND year = :year")
    fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}
