package com.dante.calorietracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dante.calorietracker.core.database.model.TrackedFoodEntity

@Database(entities = [TrackedFoodEntity::class], version = 1, exportSchema = true)
abstract class CalorieTrackerDatabase : RoomDatabase() {
    abstract fun dao(): CalorieTrackerDao
}
