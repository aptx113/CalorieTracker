package com.dante.calorietracker.feature.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerOutlinedButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.tracker.component.DaySelector
import com.dante.calorietracker.feature.tracker.component.ExpandableMeal
import com.dante.calorietracker.feature.tracker.component.NutrientsHeader
import com.dante.calorietracker.feature.tracker.component.TrackedFoodItem
import com.dante.calorietracker.feature.tracker.model.Meal
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState

@Composable
internal fun TrackerOverviewRoute(
    onNavigated: (String) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    TrackerOverviewScreen(
        trackerState = viewModel.state,
        onNavigated = onNavigated,
        onPreviousDayClick = viewModel::onPreviousDayClick,
        onNextDayClick = viewModel::onNextDayClick,
        onToggleClick = viewModel::onToggleMealClick,
        onDeleteClick = viewModel::onDeleteTrackedFoodClick,
        onAddFoodClick = viewModel::onAddFoodClick
    )
}

@Composable
internal fun TrackerOverviewScreen(
    trackerState: TrackerOverviewState,
    onNavigated: (String) -> Unit,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onToggleClick: (Meal) -> Unit,
    onDeleteClick: (TrackedFood) -> Unit,
    onAddFoodClick: (Meal, (String) -> Unit) -> Unit
) {
    val spacing = LocalDimens.current
    Column(modifier = Modifier.fillMaxSize()) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = spacing.space16)
        ) {
            items(trackerState.meals) { meal ->
                ExpandableMeal(
                    meal = meal,
                    onToggleClick = onToggleClick,
                    content = {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = spacing.space8),
                            verticalArrangement = Arrangement.spacedBy(spacing.space16)
                        ) {
                            items(trackerState.trackedFoods) { food ->
                                TrackedFoodItem(
                                    trackedFood = food,
                                    onDeleteClick = { onDeleteClick(food) }
                                )
                            }
                        }
                        CalorieTrackerOutlinedButton(
                            onClick = { onAddFoodClick(meal) { onNavigated(it) } },
                            text = {
                                Text(
                                    text = stringResource(
                                        id = R.string.add_meal,
                                        meal.name
                                    )
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = CalorieTrackerIcons.Add,
                                    contentDescription = stringResource(id = R.string.add)
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
                onToggleClick = {},
                onDeleteClick = {},
                onAddFoodClick = { _, _ -> }
            )
        }
    }
}
