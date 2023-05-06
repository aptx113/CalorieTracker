package com.dante.calorietracker.core.testing.data.repository

import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.model.UserInfo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

val emptyUserInfo = UserInfo(
    gender = Gender.Other,
    age = 0,
    height = 0f,
    weight = 0f,
    activityLevel = ActivityLevel.High,
    goalType = GoalType.KeepWeight,
    carbRatio = 0f,
    proteinRatio = 0f,
    fatRatio = 0f
)

class TestUserDataRepository : UserDataRepository {

    private val _userInfo =
        MutableSharedFlow<UserInfo>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentUserInfo get() = _userInfo.replayCache.firstOrNull() ?: emptyUserInfo

    override val userInfo: Flow<UserInfo> = _userInfo.filterNotNull()

    override suspend fun saveGender(gender: Gender) {
        _userInfo.tryEmit(currentUserInfo.copy(gender = gender))
    }

    override suspend fun saveAge(age: Int) {
        _userInfo.tryEmit(currentUserInfo.copy(age = age))
    }

    override suspend fun saveWeight(weight: Float) {
        _userInfo.tryEmit(currentUserInfo.copy(weight = weight))
    }

    override suspend fun saveHeight(height: Float) {
        _userInfo.tryEmit(currentUserInfo.copy(height = height))
    }

    override suspend fun saveActivityLevel(activityLevel: ActivityLevel) {
        _userInfo.tryEmit(currentUserInfo.copy(activityLevel = activityLevel))
    }

    override suspend fun saveGoalType(goalType: GoalType) {
        _userInfo.tryEmit(currentUserInfo.copy(goalType = goalType))
    }

    override suspend fun saveCarbRatio(carbRatio: Float) {
        _userInfo.tryEmit(currentUserInfo.copy(carbRatio = carbRatio))
    }

    override suspend fun saveProteinRatio(proteinRatio: Float) {
        _userInfo.tryEmit(currentUserInfo.copy(proteinRatio = proteinRatio))
    }

    override suspend fun saveFatRatio(fatRatio: Float) {
        _userInfo.tryEmit(currentUserInfo.copy(fatRatio = fatRatio))
    }
}
