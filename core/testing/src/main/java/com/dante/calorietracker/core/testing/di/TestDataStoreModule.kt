package com.dante.calorietracker.core.testing.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.dante.calorietracker.core.datastore.di.DataStoreModule
import com.dante.calorietracker.core.network.CalorieTrackerDispatcher.IO
import com.dante.calorietracker.core.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.junit.rules.TemporaryFolder
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataStoreModule::class])
object TestDataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        tmpFolder: TemporaryFolder
    ): DataStore<Preferences> =
        tmpFolder.testUserPreferencesDataStore(coroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher))
}

fun TemporaryFolder.testUserPreferencesDataStore(coroutineScope: CoroutineScope) =
    PreferenceDataStoreFactory.create(scope = coroutineScope) {
        newFile("user_preferences_test.preferences_pb")
    }
