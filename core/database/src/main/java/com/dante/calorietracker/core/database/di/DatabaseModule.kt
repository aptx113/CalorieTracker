package com.dante.calorietracker.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dante.calorietracker.core.database.CalorieTrackerDao
import com.dante.calorietracker.core.database.CalorieTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): RoomDatabase = Room.databaseBuilder(
        context,
        CalorieTrackerDatabase::class.java,
        "calorie_tracker_database"
    ).build()

    @Provides
    fun providesTrackerDao(database: RoomDatabase): CalorieTrackerDao =
        (database as CalorieTrackerDatabase).dao()
}
