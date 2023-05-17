package com.dante.calorietracker.feature.weight

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
internal fun WeightRoute(
    onNavigated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WeightViewModel = hiltViewModel()
) {

    val weight by viewModel.weight.collectAsStateWithLifecycle()
    WeightScreen(
        modifier = modifier,
        onWeightEnter = viewModel::onWeightEnter,
        onNextClick = { viewModel.onNextClick(onNavigated) },
        weight = weight,
        shouldDisplayWeightNotFilled = viewModel.shouldDisplayWeightNotFilled,
        clearWeightNotFilledState = viewModel::clearWeightNotFilledState
    )
}

@Composable
internal fun WeightScreen(
    modifier: Modifier = Modifier,
    onWeightEnter: (String) -> Unit = {},
    onNextClick: () -> Unit = {},
    weight: String = "",
    shouldDisplayWeightNotFilled: Boolean = false,
    clearWeightNotFilledState: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val weightIsEmptyMessage = stringResource(id = R.string.error_weight_cant_be_empty)
    val confirmTest = stringResource(id = R.string.confirm)
    val snackBarDelegate = LocalSnackBarDelegate.current

    LaunchedEffect(shouldDisplayWeightNotFilled) {
        if (shouldDisplayWeightNotFilled) {
            val snackBarResult = snackBarDelegate.showSnackBarAsync(
                message = weightIsEmptyMessage,
                actionLabel = confirmTest,
                duration = SnackbarDuration.Short
            )?.await()
            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> {
                    clearWeightNotFilledState()
                }

                else -> clearWeightNotFilledState()
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
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            UnitTextField(
                value = weight,
                onValueChange = onWeightEnter,
                unit = stringResource(id = R.string.kg),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onNextClick()
                }),
                modifier = Modifier.testTag(WEIGHT_TEXT_FIELD)
            )
        }
        CalorieTrackerButton(
            onClick = onNextClick, modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@ThemePreviews
@Composable
fun HeightScreenPrev() {
    CalorieTrackerTheme {
        Background {
            WeightScreen()
        }
    }
}

const val WEIGHT_TEXT_FIELD = "weightTextField"