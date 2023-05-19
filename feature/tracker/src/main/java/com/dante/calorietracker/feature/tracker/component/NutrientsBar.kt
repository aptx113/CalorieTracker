package com.dante.calorietracker.feature.tracker.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.dante.calorietracker.core.ui.theme.LocalCarbsTheme
import com.dante.calorietracker.core.ui.theme.LocalFatTheme
import com.dante.calorietracker.core.ui.theme.LocalProteinTheme

@Composable
fun NutrientBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colorScheme.background
    val caloriesExceedColor = MaterialTheme.colorScheme.error
    val fatColor = LocalFatTheme.current.color
    val proteinColor = LocalProteinTheme.current.color
    val carbsColor = LocalCarbsTheme.current.color

    val carbsWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatsWidthRatio = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = carbs) {
        carbsWidthRatio.animateTo(
            targetValue = ((carbs * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(
            targetValue = ((protein * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = fat) {
        fatsWidthRatio.animateTo(
            targetValue = ((fat * 9f) / caloriesGoal)
        )
    }
    Canvas(modifier = modifier) {
        if (calories <= caloriesGoal) {
            val carbsWidth = carbsWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatsWidth = fatsWidthRatio.value * size.width
            drawRoundRect(color = background, size = size, cornerRadius = CornerRadius(100f))
            drawRect(
                color = fatColor,
                size = Size(width = carbsWidth + proteinWidth + fatsWidth, height = size.height),
            )
            drawRect(
                color = proteinColor,
                size = Size(width = carbsWidth + proteinWidth, height = size.height),
            )
            drawRect(
                color = carbsColor,
                size = Size(width = carbsWidth, height = size.height),
            )
        } else {
            drawRoundRect(color = caloriesExceedColor, size = size, cornerRadius = CornerRadius(100f))
        }
    }
}
