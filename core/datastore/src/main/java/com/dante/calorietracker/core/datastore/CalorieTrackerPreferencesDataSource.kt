package com.dante.calorietracker.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class CalorieTrackerPreferencesDataSource @Inject constructor(private val calorieTrackerPreferences: DataStore<Preferences>) {
}
