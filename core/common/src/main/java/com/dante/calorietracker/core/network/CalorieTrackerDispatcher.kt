package com.dante.calorietracker.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val calorieTrackerDispatcher: CalorieTrackerDispatcher)

enum class CalorieTrackerDispatcher {
    Default, IO
}
