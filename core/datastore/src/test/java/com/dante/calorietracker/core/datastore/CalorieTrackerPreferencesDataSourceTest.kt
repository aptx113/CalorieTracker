package com.dante.calorietracker.core.datastore

import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.testing.di.testUserPreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals

class CalorieTrackerPreferencesDataSourceTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var dataSource: CalorieTrackerPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        dataSource =
            CalorieTrackerPreferencesDataSource(tmpFolder.testUserPreferencesDataStore(testScope))
    }

    @Test
    fun userGender_saveMale_returnMale() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().gender, Gender.Other)
        dataSource.saveGender(Gender.Male)
        assertEquals(dataSource.userInfo.first().gender, Gender.Male)
    }

    @Test
    fun userAge_saveTen_returnTen() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().age, 0)
        dataSource.saveAge(10)
        assertEquals(dataSource.userInfo.first().age, 10)
    }

    @Test
    fun userWeight_saveForty_returnForty() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().weight, 0f)
        dataSource.saveWeight(40f)
        assertEquals(dataSource.userInfo.first().weight, 40f)
    }

    @Test
    fun userHeight_saveOneHundredForty_returnOneHundredForty() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().height, 0f)
        dataSource.saveHeight(140f)
        assertEquals(dataSource.userInfo.first().height, 140f)
    }

    @Test
    fun userActivityLevel_saveHigh_returnHigh() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().activityLevel, ActivityLevel.Low)
        dataSource.saveActivityLevel(ActivityLevel.High)
        assertEquals(dataSource.userInfo.first().activityLevel, ActivityLevel.High)
    }

    @Test
    fun userGoalType_saveLoseWeightGoalType_returnLoseWeight() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().goalType, GoalType.KeepWeight)
        dataSource.saveGoalType(GoalType.GainWeight)
        assertEquals(dataSource.userInfo.first().goalType, GoalType.GainWeight)
    }

    @Test
    fun userCarbRatio_saveCarbRatioThirty_returnThirty() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().carbRatio, 0f)
        dataSource.saveCarbRatio(30f)
        assertEquals(dataSource.userInfo.first().carbRatio, 30f)
    }

    @Test
    fun userProteinRatio_saveProteinRatioThirty_returnThirty() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().proteinRatio, 0f)
        dataSource.saveProteinRatio(30f)
        assertEquals(dataSource.userInfo.first().proteinRatio, 30f)
    }

    @Test
    fun userFatRatio_saveFatRatioThirty_returnThirty() = testScope.runTest {
        assertEquals(dataSource.userInfo.first().fatRatio, 0f)
        dataSource.saveFatRatio(30f)
        assertEquals(dataSource.userInfo.first().fatRatio, 30f)
    }
}
