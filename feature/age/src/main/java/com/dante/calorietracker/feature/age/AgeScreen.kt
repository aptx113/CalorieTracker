package com.dante.calorietracker.feature.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.AppButton
import com.dante.calorietracker.core.ui.component.UnitTextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AgeScreen(
    modifier: Modifier = Modifier,
    onAgeEntered: (String) -> Unit,
    onNextClick: () -> Unit,
    age: String = "",
    shouldDisplayAgeNotFilled: Boolean = false,
    clearAgeNotFilledState: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val ageIsEmptyMessage = stringResource(id = R.string.error_age_cant_be_empty)
    val confirmTest = stringResource(id = R.string.confirm)
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(shouldDisplayAgeNotFilled) {
        if (shouldDisplayAgeNotFilled) {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = ageIsEmptyMessage,
                actionLabel = confirmTest,
                duration = SnackbarDuration.Short
            )
            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> {
                    clearAgeNotFilledState()
                }

                else -> clearAgeNotFilledState()
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        contentWindowInsets = WindowInsets(32.dp, 32.dp, 32.dp, 32.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
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
                    unit = stringResource(id = R.string.years)
                )
            }
            AppButton(onClick = onNextClick, modifier = Modifier.align(Alignment.BottomEnd)) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}
