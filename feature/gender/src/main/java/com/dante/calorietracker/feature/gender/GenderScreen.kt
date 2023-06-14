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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerButton
import com.dante.calorietracker.core.ui.component.CalorieTrackerSuggestionChip
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens

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
    val spacing = LocalDimens.current
    val genders = listOf(Gender.Male, Gender.Female)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.space32)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            Row {
                genders.forEach {
                    CalorieTrackerSuggestionChip(
                        isSelected = selectedGender == it,
                        onSelectedChange = { onSelectedChange(it) },
                        modifier = modifier.padding(spacing.space8)
                    ) {
                        Text(text = it.gender)
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
fun GenderScreenPrev() {
    CalorieTrackerTheme {
        Background {
            GenderScreen(selectedGender = Gender.Male, onSelectedChange = {}, onNextClick = {})
        }
    }
}
