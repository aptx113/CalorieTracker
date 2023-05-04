package com.dante.calorietracker.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
fun CalorieTrackerSuggestionChip(
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    SuggestionChip(
        onClick = { onSelectedChange(!isSelected) },
        label = {
            ProvideTextStyle(
                value = TextStyle(
                    color = if (isSelected) selectedTextColor else color,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                )
            ) {
                label()
            }
        },
        shape = RoundedCornerShape(spacing.space100),
        border = SuggestionChipDefaults.suggestionChipBorder(
            borderColor = color,
            borderWidth = spacing.space2
        ),
        colors = SuggestionChipDefaults.suggestionChipColors(if (isSelected) color else Color.Transparent),
        modifier = modifier.padding(spacing.space16)
    )
}
