package com.dante.calorietracker.feature.search

sealed interface SearchResultUiState {
    object Loading : SearchResultUiState

    object EmptyQuery : SearchResultUiState

    object LoadFailed : SearchResultUiState

    data class Success(
        val trackableFoodUiStates: List<TrackableFoodUiState> = emptyList()
    ) : SearchResultUiState {
        fun isEmpty(): Boolean = trackableFoodUiStates.isEmpty()
    }

    object SearchNotReady : SearchResultUiState
}
