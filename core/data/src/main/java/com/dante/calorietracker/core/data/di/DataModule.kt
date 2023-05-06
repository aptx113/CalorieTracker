package com.dante.calorietracker.core.data.di

import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsUserDataRepository(userDataRepository: UserDataRepositoryImpl): UserDataRepository
}
