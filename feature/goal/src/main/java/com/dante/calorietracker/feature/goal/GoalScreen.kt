package com.dante.calorietracker.feature.goal

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
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerButton
import com.dante.calorietracker.core.ui.component.CalorieTrackerSuggestionChip
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
internal fun GoalRoute(
    onNavigated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoalViewModel = hiltViewModel()
) {
    GoalScreen(
        modifier = modifier,
        selectedGoal = viewModel.selectedGoal,
        onSelectedChange = viewModel::onGoalSelect,
        onNextClick = {
            viewModel.onNextClick(onNavigated)
        })
}

@Composable
internal fun GoalScreen(
    modifier: Modifier = Modifier, selectedGoal: GoalType = GoalType.KeepWeight,
    onSelectedChange: (GoalType) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    val spacing = LocalSpacing.current
    val goalType = listOf(GoalType.GainWeight, GoalType.KeepWeight, GoalType.LoseWeight)
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
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            Row {
                goalType.forEach {
                    CalorieTrackerSuggestionChip(
                        isSelected = selectedGoal.goal == it.goal,
                        onSelectedChange = { onSelectedChange(it) },
                        modifier = Modifier.padding(spacing.space8)
                    ) {
                        Text(
                            text = when (it) {
                                GoalType.GainWeight -> stringResource(id = R.string.gain)
                                GoalType.KeepWeight -> stringResource(id = R.string.keep)
                                else -> stringResource(id = R.string.lose)
                            }
                        )
                    }
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
fun GoalScreenPrev() {
    CalorieTrackerTheme {
        Background {
            GoalScreen()
        }
    }
}
