package com.dante.calorietracker.core.datastore

import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    suspend fun saveGender(gender: Gender)
    val gender: Flow<Gender>
    suspend fun saveAge(age: Int)
    val age: Flow<Int>
    suspend fun saveWeight(weight: Float)
    val weight: Flow<Float>
    suspend fun saveHeight(height: Float)
    val height: Flow<Float>
    suspend fun saveActivityLevel(activityLevel: ActivityLevel)
    val activityLevel: Flow<ActivityLevel>
    suspend fun saveGoalType(goalType: GoalType)
    val goalType: Flow<GoalType>
    suspend fun saveCarbRatio(carbRatio: Float)
    val carbRatio: Flow<Float>
    suspend fun saveProteinRatio(proteinRatio: Float)
    val proteinRatio: Flow<Float>
    suspend fun saveFatRatio(fatRatio: Float)
    val fatRatio: Flow<Float>
}
