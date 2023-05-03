package com.dante.calorietracker.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
//    @Singleton
//    @Provides
//    fun providesDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
//        return PreferenceDataStoreFactory.create(migrations =  listOf(
//            SharedPreferencesMigration(context, PREFS_NAME)
//        ),)
//    }
}
