package com.dante.calorietracker.feature.weight

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
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.ui.delegate.LocalSnackBarDelegate
import com.dante.calorietracker.core.ui.delegate.SnackBarDelegateImpl
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class WeightScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = spyk(TestUserDataRepository())
    private val viewModel =
        WeightViewModel(repository, DecimalValidatorUseCase(), SavedStateHandle())

    private val kg = "kg"

    @Test
    fun isWeightScreenDisplayed() {
        setWeightScreen()
        composeTestRule
            .onNodeWithText(kg)
            .assertExists()
    }

    @Test
    fun weightTextField_fillWeight() {
        setWeightScreen(weight = "66.7")
        composeTestRule
            .onNodeWithTag(WEIGHT_TEXT_FIELD)
            .assert(hasText("66.7"))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun weightTextField_noValue_showSnackBar() = runTest {
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
                    WeightScreen(
                        modifier = Modifier.padding(it),
                        onNextClick = { viewModel.onNextClick { } },
                        shouldDisplayWeightNotFilled = viewModel.shouldDisplayWeightNotFilled
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

    private fun setWeightScreen(
        onWeightEnter: (String) -> Unit = {},
        onNextClick: () -> Unit = {},
        weight: String = "",
        shouldDisplayWeightNotFilled: Boolean = false
    ) {
        composeTestRule.setContent {
            WeightScreen(
                onWeightEnter = onWeightEnter,
                onNextClick = onNextClick,
                weight = weight,
                shouldDisplayWeightNotFilled = shouldDisplayWeightNotFilled
            )
        }
    }
}
