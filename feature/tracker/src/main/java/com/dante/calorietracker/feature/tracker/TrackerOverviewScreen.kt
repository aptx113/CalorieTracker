package com.dante.calorietracker.feature.tracker

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.tracker.component.DaySelector
import com.dante.calorietracker.feature.tracker.component.ExpandableMeal
import com.dante.calorietracker.feature.tracker.component.NutrientsHeader
import com.dante.calorietracker.feature.tracker.model.Meal
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState

@Composable
internal fun TrackerOverviewRoute(
    onNavigated: () -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    TrackerOverviewScreen(
        trackerState = viewModel.state,
        onNavigated = onNavigated,
        onPreviousDayClick = viewModel::onPreviousDayClick,
        onNextDayClick = viewModel::onNextDayClick,
        onToggleClick = viewModel::onToggleMealClick
    )
}

@Composable
internal fun TrackerOverviewScreen(
    trackerState: TrackerOverviewState, onNavigated: () -> Unit, onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit, onToggleClick: (Meal) -> Unit
) {
    val spacing = LocalDimens.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.space16)
    ) {
        item {
            NutrientsHeader(state = trackerState)
            Spacer(modifier = Modifier.height(spacing.space16))
            DaySelector(
                date = trackerState.date,
                onPreviousDayClick = onPreviousDayClick,
                onNextDayClick = onNextDayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.space16)
            )
            Spacer(modifier = Modifier.height(spacing.space16))
        }
        items(trackerState.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = onToggleClick,
                content = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ThemePreviews
@Composable
fun TrackerOverviewScreenPrev() {
    CalorieTrackerTheme {
        Background {
            TrackerOverviewScreen(
                trackerState = TrackerOverviewState(),
                onNavigated = {},
                onPreviousDayClick = {},
                onNextDayClick = {},
                onToggleClick = {}
            )
        }
    }
}
