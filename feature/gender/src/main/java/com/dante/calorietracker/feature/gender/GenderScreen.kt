package com.dante.calorietracker.feature.gender

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.dante.calorietracker.core.ui.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.ui.component.AppButton
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerSuggestionChip
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
internal fun GenderRoute(
    onAgeNavigated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenderViewModel = hiltViewModel()
) {
    GenderScreen(
        selectedGender = viewModel.selectedGender,
        onSelectedChange = { viewModel.onGenderClick(it) },
        onNextClick = {
            viewModel.onNextClick()
            onAgeNavigated()
        },
        modifier = modifier
    )
}

@Composable
internal fun GenderScreen(
    selectedGender: Gender,
    onSelectedChange: (Gender) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.space32)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            Row {
                CalorieTrackerSuggestionChip(
                    isSelected = selectedGender is Gender.Male,
                    color = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = Color.White,
                    onSelectedChange = { onSelectedChange(Gender.Male) }
                ) {
                    Text(text = stringResource(id = R.string.male))
                }
                CalorieTrackerSuggestionChip(
                    isSelected = selectedGender is Gender.Female,
                    color = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = Color.White,
                    onSelectedChange = { onSelectedChange(Gender.Female) }
                ) {
                    Text(text = stringResource(id = R.string.female))
                }
                CalorieTrackerSuggestionChip(
                    isSelected = selectedGender is Gender.Other,
                    color = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = Color.White,
                    onSelectedChange = { onSelectedChange(Gender.Other) }
                ) {
                    Text(text = stringResource(id = R.string.other))
                }
            }
        }
        AppButton(onClick = onNextClick, modifier = Modifier.align(Alignment.BottomEnd)) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@ThemePreviews
@Composable
fun GenderScreenPrev() {
    CalorieTrackerTheme {
        Background {
            GenderScreen(selectedGender = Gender.Male, onSelectedChange = {}, onNextClick = {})
        }
    }
}
