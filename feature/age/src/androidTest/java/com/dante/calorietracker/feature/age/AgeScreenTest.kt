package com.dante.calorietracker.feature.age

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue


class AgeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val repository = TestUserDataRepository()
    private val viewModel = AgeViewModel(repository, FilterOutDigitsUseCase(), SavedStateHandle())

    private val ageTextField = "ageTextField"
    private lateinit var next:String

    @Before
    fun setup(){
        composeTestRule.activity.apply{
            next = "Next"
        }
    }

    @Test
    fun ageTextField_fillAge() {
        composeTestRule.setContent {
            val age = "32"
            AgeScreen(age = age)
        }
        composeTestRule
            .onNodeWithTag(ageTextField)
            .assert(hasText("32"))
    }

    @Test
    fun emptyAge_clickNext_showSnackBar() {
        composeTestRule.setContent {
            AgeScreen(
                onNextClick = { viewModel.onNextClick { } },
                shouldDisplayAgeNotFilled = viewModel.shouldDisplayAgeNotFilled
            )
        }
        composeTestRule
            .onNodeWithText(next)
            .performClick()

        assertTrue(viewModel.shouldDisplayAgeNotFilled)
    }
}