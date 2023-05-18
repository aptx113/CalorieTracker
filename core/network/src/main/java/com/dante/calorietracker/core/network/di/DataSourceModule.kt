package com.dante.calorietracker.core.network.di

import com.dante.calorietracker.core.network.CalorieTrackerDataSource
import com.dante.calorietracker.core.network.RetrofitCalorieTrackerNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindsTrackerDataSource(dataSource: RetrofitCalorieTrackerNetwork): CalorieTrackerDataSource
}
