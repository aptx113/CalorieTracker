package com.dante.calorietracker.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.model.ActivityLevel.Companion.getActivityLevelFromString
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.model.Gender.Companion.getGenderFromString
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val genderKey = "genderKey"
private const val ageKey = "ageKey"
private const val weightKey = "weightKey"
private const val heightKey = "heightKey"
private const val activityLevelKey = "activityLevelKey"
private const val goalTypeKey = "goalTypeKey"
private const val carbRatioKey = "carbRatioKey"
private const val proteinRatioKey = "proteinRatioKey"
private const val fatRatioKey = "fatRatioKey"

class CalorieTrackerPreferencesDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) :
    CalorieTrackerPreferences {

    private object PreferencesKey {
        val GenderKey = stringPreferencesKey(genderKey)
        val AgeKey = intPreferencesKey(ageKey)
        val WeightKey = floatPreferencesKey(weightKey)
        val HeightKey = floatPreferencesKey(heightKey)
        val ActivityLevelKey = stringPreferencesKey(activityLevelKey)
        val GoalTypeKey = stringPreferencesKey(goalTypeKey)
        val CarbRatioKey = floatPreferencesKey(carbRatioKey)
        val ProteinRatioKey = floatPreferencesKey(proteinRatioKey)
        val FatRatioKey = floatPreferencesKey(fatRatioKey)
    }

    val userInfo = dataStore.data.map {
        UserInfo(
            gender = (it[PreferencesKey.GenderKey] ?: "").getGenderFromString(),
            age = it[PreferencesKey.AgeKey] ?: 0,
            weight = it[PreferencesKey.WeightKey] ?: 0f,
            height = it[PreferencesKey.HeightKey] ?: 0f,
            activityLevel = getActivityLevelFromString(it[PreferencesKey.ActivityLevelKey] ?: ""),
            goalType = GoalType.getGoalTypeFromString(it[PreferencesKey.GoalTypeKey] ?: ""),
            carbRatio = it[PreferencesKey.CarbRatioKey] ?: 0f,
            proteinRatio = it[PreferencesKey.ProteinRatioKey] ?: 0f,
            fatRatio = it[PreferencesKey.FatRatioKey] ?: 0f
        )
    }

    override suspend fun saveGender(gender: Gender) {
        dataStore.editPreferences(PreferencesKey.GenderKey, gender.name)
    }

    override val gender: Flow<Gender>
        get() = dataStore.getPreferencesFlow(PreferencesKey.GenderKey, "Other")
            .map { it.getGenderFromString() }

    override suspend fun saveAge(age: Int) {
        dataStore.editPreferences(PreferencesKey.AgeKey, age)
    }

    override val age: Flow<Int>
        get() = dataStore.getPreferencesFlow(PreferencesKey.AgeKey, 1)

    override suspend fun saveWeight(weight: Float) {
        dataStore.editPreferences(PreferencesKey.WeightKey, weight)
    }

    override val weight: Flow<Float>
        get() = dataStore.getPreferencesFlow(PreferencesKey.WeightKey, 1f)

    override suspend fun saveHeight(height: Float) {
        dataStore.editPreferences(PreferencesKey.HeightKey, height)
    }

    override val height: Flow<Float>
        get() = dataStore.getPreferencesFlow(PreferencesKey.HeightKey, 10f)

    override suspend fun saveActivityLevel(activityLevel: ActivityLevel) {
        dataStore.editPreferences(PreferencesKey.ActivityLevelKey, activityLevel.name)
    }

    override val activityLevel: Flow<ActivityLevel>
        get() = dataStore.getPreferencesFlow(PreferencesKey.ActivityLevelKey, "").map {
            getActivityLevelFromString(it)
        }

    override suspend fun saveGoalType(goalType: GoalType) {
        dataStore.editPreferences(PreferencesKey.GoalTypeKey, goalType.name)
    }

    override val goalType: Flow<GoalType>
        get() = dataStore.getPreferencesFlow(PreferencesKey.GoalTypeKey, "")
            .map { GoalType.getGoalTypeFromString(it) }

    override suspend fun saveCarbRatio(carbRatio: Float) {
        dataStore.editPreferences(PreferencesKey.CarbRatioKey, carbRatio)
    }

    override val carbRatio: Flow<Float>
        get() = dataStore.getPreferencesFlow(PreferencesKey.CarbRatioKey, 0f)

    override suspend fun saveProteinRatio(proteinRatio: Float) {
        dataStore.editPreferences(PreferencesKey.ProteinRatioKey, proteinRatio)
    }

    override val proteinRatio: Flow<Float>
        get() = dataStore.getPreferencesFlow(PreferencesKey.ProteinRatioKey, 0f)

    override suspend fun saveFatRatio(fatRatio: Float) {
        dataStore.editPreferences(PreferencesKey.FatRatioKey, fatRatio)
    }

    override val fatRatio: Flow<Float>
        get() = dataStore.getPreferencesFlow(PreferencesKey.FatRatioKey, 0f)
}

suspend fun <T> DataStore<Preferences>.editPreferences(
    preferencesKey: Preferences.Key<T>,
    value: T
): Preferences = edit { mutablePreferences -> mutablePreferences[preferencesKey] = value }

fun <T> DataStore<Preferences>.getPreferencesFlow(
    preferencesKey: Preferences.Key<T>,
    elvisValue: T
): Flow<T> = data.catch { exception ->
    if (exception is IOException) emit(
        emptyPreferences()
    ) else throw exception
}.map { preferences -> preferences[preferencesKey] ?: elvisValue }
