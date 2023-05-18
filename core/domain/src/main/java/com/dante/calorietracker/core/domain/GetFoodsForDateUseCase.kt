package com.dante.calorietracker.core.domain

import com.dante.calorietracker.core.data.repository.TrackerRepository
import com.dante.calorietracker.core.model.TrackedFood
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@ViewModelScoped
class GetFoodsForDateUseCase @Inject constructor(private val repository: TrackerRepository) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> =
        repository.getFoodForDate(date)
}
