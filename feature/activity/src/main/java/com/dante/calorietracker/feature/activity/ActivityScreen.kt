package com.dante.calorietracker.feature.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerButton
import com.dante.calorietracker.core.ui.component.CalorieTrackerSuggestionChip
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
internal fun ActivityRoute(
    onNavigated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ActivityViewModel = hiltViewModel()
) {
    ActivityScreen(
        modifier = modifier,
        selectedActivity = viewModel.selectedActivity,
        onSelectedChange = viewModel::onActivityLevelClick,
        onNextClick = {
            viewModel.onNextClick(onNavigated)
        })
}

@Composable
internal fun ActivityScreen(
    modifier: Modifier = Modifier,
    selectedActivity: ActivityLevel = ActivityLevel.Medium,
    onSelectedChange: (ActivityLevel) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.space32)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            Row {
                CalorieTrackerSuggestionChip(
                    isSelected = selectedActivity is ActivityLevel.High,
                    onSelectedChange = { onSelectedChange(ActivityLevel.High) },
                    modifier = modifier.padding(spacing.space8)
                ) {
                    Text(text = stringResource(id = R.string.high))
                }
                CalorieTrackerSuggestionChip(
                    isSelected = selectedActivity is ActivityLevel.Medium,
                    onSelectedChange = { onSelectedChange(ActivityLevel.Medium) },
                    modifier = modifier.padding(spacing.space8)
                ) {
                    Text(text = stringResource(id = R.string.medium))
                }
                CalorieTrackerSuggestionChip(
                    isSelected = selectedActivity is ActivityLevel.Low,
                    onSelectedChange = { onSelectedChange(ActivityLevel.Low) },
                    modifier = modifier.padding(spacing.space8)
                ) {
                    Text(text = stringResource(id = R.string.low))
                }
            }
        }
        CalorieTrackerButton(
            onClick = onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@ThemePreviews
@Composable
fun ActivityScreenPrev() {
    CalorieTrackerTheme {
        Background {
            ActivityScreen()
        }
    }
}
