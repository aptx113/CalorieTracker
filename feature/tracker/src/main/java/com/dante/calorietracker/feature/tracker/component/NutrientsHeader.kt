package com.dante.calorietracker.feature.tracker.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.component.UnitDisplay
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.theme.LocalCarbsTheme
import com.dante.calorietracker.core.ui.theme.LocalFatTheme
import com.dante.calorietracker.core.ui.theme.LocalProteinTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.tracker.model.TrackerOverviewState

@Composable
internal fun NutrientsHeader(state: TrackerOverviewState, modifier: Modifier = Modifier) {
    val dimens = LocalDimens.current
    val animatedCalorieCount = animateIntAsState(targetValue = state.totalCalories)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = dimens.space32, vertical = dimens.space64)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            UnitDisplay(
                amount = animatedCalorieCount.value,
                unit = stringResource(id = R.string.kcal),
                amountTextSize = dimens.size40,
                amountColor = MaterialTheme.colorScheme.onPrimary,
                unitColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                UnitDisplay(
                    amount = animatedCalorieCount.value,
                    unit = stringResource(id = R.string.kcal),
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextSize = dimens.size40,
                    unitColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(dimens.space8))
        NutrientBar(
            carbs = state.totalCarbs,
            protein = state.totalProtein,
            fat = state.totalFat,
            calories = state.totalCalories,
            caloriesGoal = state.caloriesGoal,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Spacer(modifier = Modifier.height(dimens.space32))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            NutrientBarInfo(
                value = state.totalCarbs,
                goal = state.carbsGoal,
                name = stringResource(id = R.string.carbs),
                color = LocalCarbsTheme.current.color,
                modifier = Modifier.size(90.dp)
            )
            NutrientBarInfo(
                value = state.totalProtein,
                goal = state.proteinGoal,
                name = stringResource(id = R.string.protein),
                color = LocalProteinTheme.current.color,
                modifier = Modifier.size(90.dp)
            )
            NutrientBarInfo(
                value = state.totalFat,
                goal = state.fatGoal,
                name = stringResource(id = R.string.fat),
                color = LocalFatTheme.current.color,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
private fun NutrientsHeaderPrev() {
    CalorieTrackerTheme {
        Background {
            NutrientsHeader(
                state = TrackerOverviewState(
                    totalCalories = 2000,
                    totalCarbs = 200,
                    totalProtein = 100,
                    totalFat = 50
                )
            )
        }
    }
}
