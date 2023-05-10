package com.dante.calorietracker.feature.height

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HeightRoute(
    modifier: Modifier = Modifier,
    onNavigated: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
) {

}

@Composable
internal fun HeightScreen(
    modifier: Modifier = Modifier,
    onHeightEntered: (String) -> Unit = {},
    onNavigated: () -> Unit = {},
    height: String = "",
    shouldDisplayHeightNotFilled: Boolean = false,
    clearHeightNotFilledState: () -> Unit = {}
) {

}
