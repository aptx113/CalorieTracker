package com.dante.calorietracker.feature.height

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.ui.delegate.LocalSnackBarDelegate
import com.dante.calorietracker.core.ui.delegate.SnackBarDelegateImpl
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HeightScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = spyk(TestUserDataRepository())
    private val viewModel =
        HeightViewModel(repository, FilterOutDigitsUseCase(), SavedStateHandle())
    private val cm = "cm"

    @Test
    fun heightTextField_unitIsCm() {
        composeTestRule.setContent {
            HeightScreen()
        }
        composeTestRule
            .onNodeWithText(cm)
            .assertIsDisplayed()
    }

    @Test
    fun heightTextField_fillHeight() {
        composeTestRule.setContent {
            HeightScreen(height = "180")
        }
        composeTestRule
            .onNodeWithTag(HEIGHT_TEXT_FIELD)
            .assert(hasText("180"))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun heightTextField_noValue_showSnackBar() = runTest {
        composeTestRule.setContent {
            val snackBarHostState = remember {
                SnackbarHostState()
            }
            val defaultSnackBarDelegateImpl = SnackBarDelegateImpl(
                snackBarHostState = snackBarHostState,
                coroutineScope = rememberCoroutineScope()
            )
            Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
                CompositionLocalProvider(LocalSnackBarDelegate provides defaultSnackBarDelegateImpl) {
                    HeightScreen(
                        modifier = Modifier.padding(it),
                        onNavigated = { viewModel.onNextClick { } },
                        shouldDisplayHeightNotFilled = viewModel.shouldDisplayHeightNotFilled
                    )
                }

            }
        }
        with(composeTestRule) {
            onNodeWithText("Next")
                .performClick()

            onNodeWithText("CONFIRM")
                .assertIsDisplayed()
        }
    }
}
