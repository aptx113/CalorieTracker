package com.dante.calorietracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CalorieTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
