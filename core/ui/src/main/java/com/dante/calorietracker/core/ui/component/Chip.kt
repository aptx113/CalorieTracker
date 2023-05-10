package com.dante.calorietracker.core.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieTrackerSuggestionChip(
    isSelected: Boolean,
    onSelectedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    label: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    FilterChip(
        selected = isSelected, onClick = onSelectedChange,
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                label()
            }
        },
        modifier = modifier,
        enabled = enable,
        shape = RoundedCornerShape(spacing.space100),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onBackground,
            selectedBorderColor = MaterialTheme.colorScheme.onBackground,
            disabledBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = CalorieTrackerChipDefaults.DisabledChipContainerAlpha
            ),
            disabledSelectedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = CalorieTrackerChipDefaults.DisabledChipContainerAlpha
            ),
            selectedBorderWidth = spacing.space2
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = if (isSelected) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = CalorieTrackerChipDefaults.DisabledChipContainerAlpha
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = CalorieTrackerChipDefaults.DisabledChipContentAlpha
            ),
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onBackground
        ),
    )
}

object CalorieTrackerChipDefaults {
    const val DisabledChipContainerAlpha = 0.12f
    const val DisabledChipContentAlpha = 0.38f
}
