package com.dante.calorietracker.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import org.junit.Before

class TrackerDaoTest {

    private lateinit var dao: CalorieTrackerDao
    private lateinit var db: CalorieTrackerDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            CalorieTrackerDatabase::class.java,
        ).build()
        dao = db.dao()
    }
}

private fun testTrackedFood(id: Int = 0, day: Int = 0, month: Int = 0, year: Int) =
    TrackedFoodEntity(
        id = id,
        name = "test",
        carbs = 1,
        proteins = 1,
        fats = 1,
        imageUrl = "test",
        type = "test",
        amount = 1,
        dayOfMonth = day,
        month = month,
        year = year,
        calories = 1
    )
