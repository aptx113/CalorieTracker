package com.dante.calorietracker.core.data.repository

import app.cash.turbine.test
import com.dante.calorietracker.core.data.testdoubles.TestTrackerDao
import com.dante.calorietracker.core.data.testdoubles.TestTrackerDataSource
import com.dante.calorietracker.core.database.CalorieTrackerDao
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.network.CalorieTrackerDataSource
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class TrackerRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TrackerRepository

    private lateinit var dao: CalorieTrackerDao

    private lateinit var network: CalorieTrackerDataSource

    @Before
    fun setup() {
        dao = TestTrackerDao()
        network = TestTrackerDataSource()

        repository = TrackerRepositoryImpl(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            dao = dao,
            dataSource = network,
        )
    }

    @Test
    fun searchFood_returnListOfTrackableFood() = runTest {
        val result = repository.searchFood("Pasta", 1, 1)

        result.first().also {
            assertEquals(2, it.size)
            assertEquals("Pasta", it[0].name)
            assertEquals("Pizza", it[1].name)
        }
    }

    @Test
    fun insertTrackedFood_getInsertedFoodsForDate() = runTest {
        repository.getFoodForDate(localDate = LocalDate(2021, 1, 1)).test {
            assertEquals(emptyList(), awaitItem())

            repository.insertTrackedFood(
                TrackedFood(
                    name = "Pasta",
                    calories = 100,
                    carbs = 13,
                    fats = 2,
                    proteins = 3,
                    date = LocalDate(2021, 1, 1), imageUrl = "",
                    amount = 2, mealType = MealType.Snack
                )
            )
            assertEquals("Pasta", awaitItem().first().name)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun deleteTrackedFood_getFoodsForDate_returnEmptyList() = runTest {
        val item = TrackedFood(
            name = "Pasta",
            calories = 100,
            carbs = 13,
            fats = 2,
            proteins = 3,
            date = LocalDate(2021, 1, 1), imageUrl = "",
            amount = 2, mealType = MealType.Snack
        )
        repository.getFoodForDate(localDate = LocalDate(2021, 1, 1)).test {
            assertEquals(emptyList(), awaitItem())

            repository.insertTrackedFood(
                item
            )
            assertEquals("Pasta", awaitItem().first().name)

            repository.deleteTrackedFood(item)
            assertEquals(emptyList(), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
