package com.dante.calorietracker.feature.nutrientGoal

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.CalorieTrackerButton
import com.dante.calorietracker.core.ui.component.CalorieTrackerTextField
import com.dante.calorietracker.core.ui.component.TextFieldError
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing
import com.dante.calorietracker.core.ui.utils.Nutrient
import com.dante.calorietracker.core.ui.utils.NutrientGoalState
import com.dante.calorietracker.core.ui.utils.NutrientStateSaver
import com.dante.calorietracker.core.ui.utils.TextFieldState

@Composable
internal fun NutrientGoalRoute(
    modifier: Modifier = Modifier,
    onNavigated: () -> Unit = {},
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    NutrientGoalScreen(
        modifier = modifier,
        onDecimalValidated = viewModel::validatedValue,
        onNext = { carbs, proteins, fats ->
            viewModel.onNextClick(carbs, proteins, fats) {
                onNavigated()
            }
        },
    )
}

@Composable
internal fun NutrientGoalScreen(
    modifier: Modifier = Modifier,
    onNext: (TextFieldState, TextFieldState, TextFieldState) -> Unit = { _, _, _ -> },
    onDecimalValidated: (String, Int, Int) -> String = { _, _, _ -> "" }
) {
    val spacing = LocalSpacing.current

    val carbsState by rememberSaveable(stateSaver = NutrientStateSaver) {
        mutableStateOf(NutrientGoalState(Nutrient.Carbs))
    }
    val proteinsState by rememberSaveable(stateSaver = NutrientStateSaver) {
        mutableStateOf(NutrientGoalState(Nutrient.Protein))
    }
    val fatsState by rememberSaveable(stateSaver = NutrientStateSaver) {
        mutableStateOf(NutrientGoalState(Nutrient.Fat))
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
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.space24))
            NutrientField(
                textFieldState = carbsState,
                focusRequester = focusRequester,
                labelRes = R.string.carbs,
                suffixRes = R.string.percent_carbs,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = {
                    carbsState.validateState {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                }),
                onDecimalValidated = onDecimalValidated
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            NutrientField(
                textFieldState = proteinsState,
                focusRequester = focusRequester,
                labelRes = R.string.protein,
                suffixRes = R.string.percent_proteins,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = {
                    proteinsState.validateState {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                }),
                onDecimalValidated = onDecimalValidated
            )
            Spacer(modifier = Modifier.height(spacing.space16))
            NutrientField(
                textFieldState = fatsState,
                focusRequester = focusRequester,
                labelRes = R.string.fat,
                suffixRes = R.string.percent_fats,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    fatsState.validateState {
                        focusRequester.requestFocus()
                        onNext(carbsState, proteinsState, fatsState)
                    }
                }),
                onDecimalValidated = onDecimalValidated
            )
        }
        CalorieTrackerButton(
            onClick = { onNext(carbsState, proteinsState, fatsState) },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Composable
private fun NutrientField(
    textFieldState: TextFieldState,
    focusRequester: FocusRequester,
    @StringRes labelRes: Int,
    @StringRes suffixRes: Int,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    onDecimalValidated: (String, Int, Int) -> String,
    modifier: Modifier = Modifier,
) {
    CalorieTrackerTextField(
        value = textFieldState.text,
        onValueChange = { textFieldState.text = onDecimalValidated(it, 2, 1) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        label = stringResource(id = labelRes),
        suffix = stringResource(id = suffixRes),
        keyboardActions = keyboardActions,
        isError = textFieldState.showErrors(),
        supportingText = {
            textFieldState.getErrorRes()?.let { error -> TextFieldError(textResError = error) }
        },
        modifier = modifier
            .width(IntrinsicSize.Min)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                textFieldState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    textFieldState.enableShowError()
                }
            },
    )
}

@ThemePreviews
@Composable
fun NutrientGoalScreenPrev() {
    CalorieTrackerTheme {
        Background {
            NutrientGoalScreen()
        }
    }
}
