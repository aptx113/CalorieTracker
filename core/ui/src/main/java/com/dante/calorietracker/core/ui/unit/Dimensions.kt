package com.dante.calorietracker.core.ui.unit

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val space0: Dp = 0.dp,
    val space2: Dp = 2.dp,
    val space4: Dp = 4.dp,
    val space8: Dp = 8.dp,
    val space16: Dp = 16.dp,
    val space32: Dp = 32.dp,
    val space64: Dp = 64.dp,
    val space100: Dp = 100.dp
)

val LocalSpacing = staticCompositionLocalOf { Dimensions() }
