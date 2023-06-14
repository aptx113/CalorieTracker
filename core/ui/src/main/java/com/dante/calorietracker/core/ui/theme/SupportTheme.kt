package com.dante.calorietracker.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SupportTheme(val color: Color = Color.Unspecified)

val LocalCarbsTheme = staticCompositionLocalOf { SupportTheme() }
val LocalProteinTheme = staticCompositionLocalOf { SupportTheme() }
val LocalFatTheme = staticCompositionLocalOf { SupportTheme() }
