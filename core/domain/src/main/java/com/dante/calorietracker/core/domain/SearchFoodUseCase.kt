package com.dante.calorietracker.core.domain

import com.dante.calorietracker.core.data.repository.TrackerRepository
import com.dante.calorietracker.core.model.TrackableFood
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class SearchFoodUseCase @Inject constructor(private val repository: TrackerRepository) {
    operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Flow<List<TrackableFood>> =
        repository.searchFood(query.trim(), page, pageSize)
}
