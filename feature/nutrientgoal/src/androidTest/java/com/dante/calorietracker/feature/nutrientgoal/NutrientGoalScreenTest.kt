package com.dante.calorietracker.feature.nutrientgoal

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.feature.nutrientGoal.NutrientGoalScreen
import com.dante.calorietracker.feature.nutrientGoal.NutrientGoalViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NutrientGoalScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val repository = TestUserDataRepository()
    private val viewModel = NutrientGoalViewModel(repository, DecimalValidatorUseCase())

    private lateinit var carbsFieldMatcher: SemanticsMatcher

    private lateinit var proteinsFieldMatcher: SemanticsMatcher

    private lateinit var fatsFieldMatcher: SemanticsMatcher

    private lateinit var next: SemanticsMatcher

    private var carbsError: String = ""
    private var proteinsError: String = ""
    private var fatsError: String = ""

    @Before
    fun setup() {
        composeTestRule.activity.resources.apply {
            carbsFieldMatcher = hasText(getString(R.string.carbs))
            proteinsFieldMatcher = hasText(getString(R.string.protein))
            fatsFieldMatcher = hasText(getString(R.string.fat))
            next = hasText(getString(R.string.next))

            carbsError = getString(R.string.error_carbs_cant_be_empty)
            proteinsError = getString(R.string.error_protein_cant_be_empty)
            fatsError = getString(R.string.error_fat_cant_be_empty)
        }

        composeTestRule.setContent {
            NutrientGoalScreen(
                onNext = { carbs, proteins, fats ->
                    viewModel.onNextClick(carbs, proteins, fats) {}
                }, onDecimalValidated = viewModel::validatedValue
            )
        }
    }

    @Test
    fun allLabelsAreDisplayed() {
        composeTestRule.apply {
            onNode(carbsFieldMatcher).assertExists()
            onNode(proteinsFieldMatcher).assertExists()
            onNode(fatsFieldMatcher).assertExists()
        }
    }

    @Test
    fun carbsField_whenMoveFocusDownWithoutValue_displayError() {
        composeTestRule.onNode(carbsFieldMatcher)
            .performClick()
            .assertIsFocused()
            .performImeAction()

        composeTestRule.onNodeWithText(carbsError)
            .assertIsDisplayed()

        composeTestRule.onNode(carbsFieldMatcher)
            .performClick()
            .performTextInput("10")
        composeTestRule.onNodeWithText(carbsError)
            .assertDoesNotExist()
    }

    @Test
    fun proteinsField_whenMoveFocusDownWithoutValue_displayError() {
        composeTestRule.onNode(proteinsFieldMatcher)
            .performClick()
            .assertIsFocused()
            .performImeAction()

        composeTestRule.onNodeWithText(proteinsError)
            .assertIsDisplayed()

        composeTestRule.onNode(proteinsFieldMatcher)
            .performClick()
            .performTextInput("10")
        composeTestRule.onNodeWithText(proteinsError)
            .assertDoesNotExist()
    }

    @Test
    fun fatsField_whenMoveFocusDownWithoutValue_displayError() {
        composeTestRule.onNode(fatsFieldMatcher)
            .performClick()
            .assertIsFocused()
            .performImeAction()

        composeTestRule.onNodeWithText(fatsError)
            .assertIsDisplayed()

        composeTestRule.onNode(fatsFieldMatcher)
            .performClick()
            .performTextInput("10")
        composeTestRule.onNodeWithText(fatsError)
            .assertDoesNotExist()
    }

    @Test
    fun allFields_whenClickNextWithoutValues_displayErrors() {
        composeTestRule.onNode(next)
            .performClick()

        composeTestRule.apply {
            onNodeWithText(carbsError).assertIsDisplayed()
            onNodeWithText(proteinsError).assertIsDisplayed()
            onNodeWithText(fatsError).assertIsDisplayed()
        }
    }
}
