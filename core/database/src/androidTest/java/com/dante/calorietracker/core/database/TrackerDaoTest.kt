package com.dante.calorietracker.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

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

    @Test
    fun trackerDao_fetch_item() = runTest {
        val trackedFood = testTrackedFood(0, 17, 5, 2023)
        dao.getFoodsForDate(17, 5, 2023).test {
            dao.insertTrackedFood(trackedFood)
            assertEquals(trackedFood, awaitItem().firstOrNull { it.id == trackedFood.id })
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun trackerDao_delete_item() = runTest {
        val trackedFood = testTrackedFood(1, 16, 4, 2022)
        dao.getFoodsForDate(16, 4, 2022).test {
            dao.insertTrackedFood(trackedFood)
            assertEquals(1, awaitItem().size)
            assertEquals(trackedFood, awaitItem().first { it.id == trackedFood.id })
            dao.deleteTrackedFood(trackedFood)
            assertEquals(null, awaitItem().firstOrNull { it.id == trackedFood.id })
        }
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
