package com.dante.calorietracker.feature.tracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.tracker.component.NutrientsHeader
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState

@Composable
internal fun TrackerOverviewRoute(
    onNavigated: () -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    TrackerOverviewScreen(trackerState = viewModel.state, onNavigated = onNavigated)
}

@Composable
internal fun TrackerOverviewScreen(trackerState: TrackerOverviewState, onNavigated: () -> Unit) {
    val spacing = LocalDimens.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.space16)
    ) {
        item {
            NutrientsHeader(state = trackerState)
        }
    }
}
