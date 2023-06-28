package com.dante.calorietracker.feature.tracker.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.NutrientInfo
import com.dante.calorietracker.core.ui.component.UnitDisplay
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons
import com.dante.calorietracker.core.ui.unit.Dimensions
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.tracker.model.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: (Meal) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = LocalDimens.current
    Column(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleClick(meal) }
            .padding(dimens.space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = meal.drawableRes), contentDescription = meal.name)
            Spacer(modifier = Modifier.width(dimens.space16))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = meal.name, style = MaterialTheme.typography.titleLarge)
                    Icon(
                        imageVector = if (meal.isExpanded) {
                            CalorieTrackerIcons.ArrowUp
                        } else {
                            CalorieTrackerIcons.ArrowDown
                        },
                        contentDescription = if (meal.isExpanded) {
                            stringResource(id = R.string.collapse)
                        } else {
                            stringResource(id = R.string.extend)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(dimens.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = R.string.kcal),
                        amountTextSize = 30.sp
                    )
                    NutrientInfoContent(meal = meal, dimensions = dimens)
                }
            }
        }
        Spacer(modifier = Modifier.height(dimens.space16))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}

@Composable
fun NutrientInfoContent(meal: Meal, dimensions: Dimensions) {
    Row {
        NutrientInfo(
            name = stringResource(id = R.string.carbs),
            amount = meal.carbs,
            unit = stringResource(id = R.string.grams)
        )
        Spacer(modifier = Modifier.width(dimensions.space8))
        NutrientInfo(
            name = stringResource(id = R.string.protein),
            amount = meal.carbs,
            unit = stringResource(id = R.string.grams)
        )
        Spacer(modifier = Modifier.width(dimensions.space8))
        NutrientInfo(
            name = stringResource(id = R.string.fat),
            amount = meal.carbs,
            unit = stringResource(id = R.string.grams)
        )
    }
}
