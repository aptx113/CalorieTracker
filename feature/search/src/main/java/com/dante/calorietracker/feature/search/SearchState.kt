package com.dante.calorietracker.feature.search

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFoodList: List<TrackableFoodUiState> = emptyList()
)
