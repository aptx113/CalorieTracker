package com.dante.calorietracker.feature.tracker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.ArrowBack
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.ArrowForward
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.feature.tracker.utils.parseDateText
import kotlinx.datetime.LocalDate

@Composable
internal fun DaySelector(
    date: LocalDate,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousDayClick) {
            Icon(
                imageVector = ArrowBack,
                contentDescription = stringResource(id = R.string.previous_day)
            )
        }
        Text(text = date.parseDateText(), style = MaterialTheme.typography.headlineMedium)
        IconButton(onClick = onNextDayClick) {
            Icon(
                imageVector = ArrowForward,
                contentDescription = stringResource(id = R.string.next_day)
            )
        }
    }
}

@ThemePreviews
@Composable
fun DaySelectorPrev() {
    CalorieTrackerTheme {
        Background {
            DaySelector(
                date = LocalDate(2023, 6, 15),
                onPreviousDayClick = {},
                onNextDayClick = {}
            )
        }
    }
}
