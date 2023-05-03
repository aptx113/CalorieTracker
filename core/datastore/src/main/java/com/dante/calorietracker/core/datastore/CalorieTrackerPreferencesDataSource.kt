package com.dante.calorietracker.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalorieTrackerPreferencesDataSource @Inject constructor(private val calorieTrackerPreferences: DataStore<Preferences>):
    DataStorePreferences {
    override suspend fun saveGender(gender: Gender) {
        TODO("Not yet implemented")
    }

    override val gender: Flow<Gender>
        get() = TODO("Not yet implemented")

    override suspend fun saveAge(age: Int) {
        TODO("Not yet implemented")
    }

    override val age: Flow<Int>
        get() = TODO("Not yet implemented")

    override suspend fun saveWeight(weight: Float) {
        TODO("Not yet implemented")
    }

    override val weight: Flow<Float>
        get() = TODO("Not yet implemented")

    override suspend fun saveHeight(height: Float) {
        TODO("Not yet implemented")
    }

    override val height: Flow<Float>
        get() = TODO("Not yet implemented")

    override suspend fun saveActivityLevel(activityLevel: ActivityLevel) {
        TODO("Not yet implemented")
    }

    override val activityLevel: Flow<ActivityLevel>
        get() = TODO("Not yet implemented")

    override suspend fun saveGoalType(goalType: GoalType) {
        TODO("Not yet implemented")
    }

    override val goalType: Flow<GoalType>
        get() = TODO("Not yet implemented")

    override suspend fun saveCarbRatio(carbRatio: Float) {
        TODO("Not yet implemented")
    }

    override val carbRatio: Flow<Float>
        get() = TODO("Not yet implemented")

    override suspend fun saveProteinRatio(proteinRatio: Float) {
        TODO("Not yet implemented")
    }

    override val proteinRatio: Flow<Float>
        get() = TODO("Not yet implemented")

    override suspend fun saveFatRatio(fatRatio: Float) {
        TODO("Not yet implemented")
    }

    override val fatRatio: Flow<Float>
        get() = TODO("Not yet implemented")
}
