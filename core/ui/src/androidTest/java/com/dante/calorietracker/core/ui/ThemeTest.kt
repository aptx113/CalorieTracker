package com.dante.calorietracker.core.ui

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.dante.calorietracker.core.ui.theme.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ThemeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun darkThemeFalse_dynamicColorFalse() {
        composeTestRule.setContent {
            CalorieTrackerTheme(isDarkTheme = false, disableDynamicTheming = true) {
                val colorTheme = LightDefaultColorScheme
                assertColorSchemesEqual(colorTheme, MaterialTheme.colorScheme)
                val backgroundTheme = defaultBackgroundTheme(colorTheme)
                Assert.assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                Assert.assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorFalse() {
        composeTestRule.setContent {
            CalorieTrackerTheme(isDarkTheme = true, disableDynamicTheming = true) {
                val colorScheme = DarkDefaultColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeFalse_dynamicColorTrue() {
        composeTestRule.setContent {
            CalorieTrackerTheme(isDarkTheme = false, disableDynamicTheming = false) {
                val colorScheme = dynamicLightColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = dynamicTintThemeWithFallback(colorScheme)
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorTrue() {
        composeTestRule.setContent { 
            CalorieTrackerTheme(isDarkTheme = true, disableDynamicTheming = false) {
                val colorScheme = dynamicDarkColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = dynamicTintThemeWithFallback(colorScheme)
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Composable
    private fun dynamicLightColorSchemeWithFallback(): ColorScheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dynamicLightColorScheme(LocalContext.current)
        } else {
            LightDefaultColorScheme
        }
    }

    @Composable
    private fun dynamicDarkColorSchemeWithFallback(): ColorScheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dynamicDarkColorScheme(LocalContext.current)
        } else {
            DarkDefaultColorScheme
        }
    }

    private fun defaultBackgroundTheme(colorScheme: ColorScheme): BackgroundTheme {
        return BackgroundTheme(color = colorScheme.surface, tonalElevation = 2.dp)
    }

    private fun defaultTintTheme(): TintTheme = TintTheme()

    private fun dynamicTintThemeWithFallback(colorScheme: ColorScheme): TintTheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            TintTheme(colorScheme.primary)
        } else {
            TintTheme()
        }
    }

    private fun assertColorSchemesEqual(
        expectedColorScheme: ColorScheme,
        actualColorScheme: ColorScheme,
    ) {
        Assert.assertEquals(expectedColorScheme.primary, actualColorScheme.primary)
        Assert.assertEquals(expectedColorScheme.onPrimary, actualColorScheme.onPrimary)
        Assert.assertEquals(
            expectedColorScheme.primaryContainer,
            actualColorScheme.primaryContainer
        )
        Assert.assertEquals(
            expectedColorScheme.onPrimaryContainer,
            actualColorScheme.onPrimaryContainer
        )
        Assert.assertEquals(expectedColorScheme.secondary, actualColorScheme.secondary)
        Assert.assertEquals(expectedColorScheme.onSecondary, actualColorScheme.onSecondary)
        Assert.assertEquals(
            expectedColorScheme.secondaryContainer,
            actualColorScheme.secondaryContainer
        )
        Assert.assertEquals(
            expectedColorScheme.onSecondaryContainer,
            actualColorScheme.onSecondaryContainer,
        )
        Assert.assertEquals(expectedColorScheme.tertiary, actualColorScheme.tertiary)
        Assert.assertEquals(expectedColorScheme.onTertiary, actualColorScheme.onTertiary)
        Assert.assertEquals(
            expectedColorScheme.tertiaryContainer,
            actualColorScheme.tertiaryContainer
        )
        Assert.assertEquals(
            expectedColorScheme.onTertiaryContainer,
            actualColorScheme.onTertiaryContainer
        )
        Assert.assertEquals(expectedColorScheme.error, actualColorScheme.error)
        Assert.assertEquals(expectedColorScheme.onError, actualColorScheme.onError)
        Assert.assertEquals(
            expectedColorScheme.errorContainer,
            actualColorScheme.errorContainer
        )
        Assert.assertEquals(
            expectedColorScheme.onErrorContainer,
            actualColorScheme.onErrorContainer
        )
        Assert.assertEquals(expectedColorScheme.background, actualColorScheme.background)
        Assert.assertEquals(expectedColorScheme.onBackground, actualColorScheme.onBackground)
        Assert.assertEquals(expectedColorScheme.surface, actualColorScheme.surface)
        Assert.assertEquals(expectedColorScheme.onSurface, actualColorScheme.onSurface)
        Assert.assertEquals(
            expectedColorScheme.surfaceVariant,
            actualColorScheme.surfaceVariant
        )
        Assert.assertEquals(
            expectedColorScheme.onSurfaceVariant,
            actualColorScheme.onSurfaceVariant
        )
        Assert.assertEquals(
            expectedColorScheme.inverseSurface,
            actualColorScheme.inverseSurface
        )
        Assert.assertEquals(
            expectedColorScheme.inverseOnSurface,
            actualColorScheme.inverseOnSurface
        )
        Assert.assertEquals(expectedColorScheme.outline, actualColorScheme.outline)
    }

}
