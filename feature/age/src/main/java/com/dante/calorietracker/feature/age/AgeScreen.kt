package com.dante.calorietracker.feature.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.component.UnitTextField
import com.dante.calorietracker.core.ui.delegate.LocalSnackBarDelegate
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
internal fun AgeRoute(
    modifier: Modifier = Modifier,
    onNavigated: () -> Unit,
    viewModel: AgeViewModel = hiltViewModel()
) {
    val age by viewModel.age.collectAsStateWithLifecycle()
    AgeScreen(
        modifier = modifier,
        onAgeEntered = viewModel::onAgeEntered,
        onNextClick = {
            viewModel.onNextClick(onNavigated)
        },
        age = age,
        viewModel.shouldDisplayAgeNotFilled,
        clearAgeNotFilledState = viewModel::clearAgeNotFilledState
    )
}

@Composable
internal fun AgeScreen(
    modifier: Modifier = Modifier,
    onAgeEntered: (String) -> Unit = {},
    onNextClick: () -> Unit = {},
    age: String = "",
    shouldDisplayAgeNotFilled: Boolean = false,
    clearAgeNotFilledState: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val ageIsEmptyMessage = stringResource(id = R.string.error_age_cant_be_empty)
    val confirmTest = stringResource(id = R.string.confirm)
    val snackBarDelegate = LocalSnackBarDelegate.current

    LaunchedEffect(shouldDisplayAgeNotFilled) {
        if (shouldDisplayAgeNotFilled) {
            val snackBarResult = snackBarDelegate.showSnackBarAsync(
                message = ageIsEmptyMessage,
                actionLabel = confirmTest,
                duration = SnackbarDuration.Short
            )?.await()
            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> {
                    clearAgeNotFilledState()
                }

                else -> clearAgeNotFilledState()
            }
        }
    }

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
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            UnitTextField(
                value = age,
                onValueChange = onAgeEntered,
                unit = stringResource(id = R.string.years),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onNextClick()
                }),
                modifier = Modifier
                    .testTag("ageTextField")
            )
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
fun AgeScreenPrev() {
    CalorieTrackerTheme {
        Background {
            AgeScreen()
        }
    }
}
