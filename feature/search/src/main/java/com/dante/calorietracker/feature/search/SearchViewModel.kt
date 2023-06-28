package com.dante.calorietracker.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.ApiResult
import com.dante.calorietracker.core.asApiResult
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
import com.dante.calorietracker.core.domain.InsertTrackedFoodUseCase
import com.dante.calorietracker.core.domain.SearchFoodUseCase
import com.dante.calorietracker.core.ext.mapOrReplace
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.core.model.asMealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFoodUseCase: SearchFoodUseCase,
    private val insertTrackedFoodUseCase: InsertTrackedFoodUseCase,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _trackableFoodUiStates = MutableStateFlow<List<TrackableFoodUiState>>(listOf())
    val trackableFoodUiStates = _trackableFoodUiStates.asStateFlow()

    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val searchResultUiState: StateFlow<SearchResultUiState> = searchQuery.flatMapLatest { query ->
        if (query.length < SEARCH_QUERY_MIN_LENGTH) {
            flowOf(SearchResultUiState.SearchNotReady)
        } else {
            searchFoodUseCase(query).asApiResult().map {
                when (it) {
                    is ApiResult.Success -> {
                        val states = it.data.map { food ->
                            TrackableFoodUiState(
                                food
                            )
                        }
                        _trackableFoodUiStates.value = states
                        SearchResultUiState.Success(trackableFoodUiStates = states)
                    }

                    is ApiResult.Loading -> {
                        SearchResultUiState.Loading
                    }

                    is ApiResult.Error -> {
                        SearchResultUiState.LoadFailed
                    }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchResultUiState.Loading
    )

    fun onQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onAmountForFoodChanged(trackerFoodUiState: TrackableFoodUiState, amount: String) {
        val newState = trackerFoodUiState.copy(amount = filterOutDigitsUseCase(amount))
        _trackableFoodUiStates.value =
            _trackableFoodUiStates.value.mapOrReplace(trackerFoodUiState, newState)
    }

    fun onSearchTriggered(query: String) = viewModelScope.launch {
        searchFoodUseCase(query).asApiResult().collect {
            when (it) {
                is ApiResult.Success -> {
                    val states = it.data.map { food ->
                        TrackableFoodUiState(
                            food
                        )
                    }
                    _trackableFoodUiStates.value = states
                    SearchResultUiState.Success(trackableFoodUiStates = states)
                }

                is ApiResult.Loading -> {
                    SearchResultUiState.Loading
                }

                is ApiResult.Error -> {
                    SearchResultUiState.LoadFailed
                }
            }
        }
    }

    fun onToggleTrackableFood(trackerFoodUiState: TrackableFoodUiState, isExpanded: Boolean) {
        val newState = trackerFoodUiState.copy(isExpanded = !isExpanded)
        _trackableFoodUiStates.value =
            _trackableFoodUiStates.value.mapOrReplace(trackerFoodUiState, newState)
    }

    fun onTrackFoodClick(
        trackerFoodUiState: TrackableFoodUiState,
        searchArgs: SearchArgs,
        onTrackerOverviewNavigated: () -> Unit
    ) =
        viewModelScope.launch {
            insertTrackedFoodUseCase(
                trackableFood = trackerFoodUiState.food,
                amount = trackerFoodUiState.amount.toIntOrNull() ?: return@launch,
                mealType = searchArgs.mealType.asMealType(),
                date = LocalDate(
                    dayOfMonth = searchArgs.dayOfMonth,
                    month = Month(searchArgs.month),
                    year = searchArgs.year
                )
            )
            onTrackerOverviewNavigated()
        }
}

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 2

