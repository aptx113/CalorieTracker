package com.dante.calorietracker.core.data.repository

import com.dante.calorietracker.core.datastore.CalorieTrackerPreferences
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.model.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(private val dataSource: CalorieTrackerPreferences) :
    UserDataRepository {
    override val userInfo: Flow<UserInfo>
        get() = dataSource.userInfo

    override suspend fun saveGender(gender: Gender) {
        dataSource.saveGender(gender)
    }

    override suspend fun saveAge(age: Int) {
        dataSource.saveAge(age)
    }

    override suspend fun saveWeight(weight: Float) {
        dataSource.saveWeight(weight)
    }

    override suspend fun saveHeight(height: Float) {
        dataSource.saveHeight(height)
    }

    override suspend fun saveActivityLevel(activityLevel: ActivityLevel) {
        dataSource.saveActivityLevel(activityLevel)
    }

    override suspend fun saveGoalType(goalType: GoalType) {
        dataSource.saveGoalType(goalType)
    }

    override suspend fun saveCarbRatio(carbRatio: Float) {
        dataSource.saveCarbRatio(carbRatio)
    }

    override suspend fun saveProteinRatio(proteinRatio: Float) {
        dataSource.saveProteinRatio(proteinRatio)
    }

    override suspend fun saveFatRatio(fatRatio: Float) {
        dataSource.saveFatRatio(fatRatio)
    }
}
