package com.dante.calorietracker.core.domain

import com.dante.calorietracker.core.data.repository.TrackerRepository
import com.dante.calorietracker.core.model.TrackedFood
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteTrackedFoodUseCase @Inject constructor(private val repository: TrackerRepository) {
   suspend operator fun invoke(trackedFood: TrackedFood){
       repository.deleteTrackedFood(trackedFood)
   }
}
