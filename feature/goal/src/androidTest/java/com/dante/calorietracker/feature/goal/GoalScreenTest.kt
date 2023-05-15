package com.dante.calorietracker.feature.goal

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.ui.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GoalScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var gainWeightChipMatcher: SemanticsMatcher

    private lateinit var keepWeightChipMatcher: SemanticsMatcher

    private lateinit var loseWeightChipMatcher: SemanticsMatcher

    private val viewModel = GoalViewModel(TestUserDataRepository())

    @Before
    fun setup() {
        composeTestRule.activity.resources.apply {
            gainWeightChipMatcher = hasText(getString(R.string.gain))
            keepWeightChipMatcher = hasText(getString(R.string.keep))
            loseWeightChipMatcher = hasText(getString(R.string.lose))
        }
        composeTestRule.setContent {
            GoalScreen(
                selectedGoal = viewModel.selectedGoal,
                onSelectedChange = { viewModel.onGoalSelect(it) },
                onNextClick = {},
            )
        }
    }

    @Test
    fun goalKeepGoalByDefault_isSelected() {
        composeTestRule
            .onNode(keepWeightChipMatcher)
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickGain_gainIsSelected() {
        composeTestRule
            .onNode(gainWeightChipMatcher)
            .performClick()
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickLose_LoseIsSelected() {
        composeTestRule
            .onNode(loseWeightChipMatcher)
            .performClick()
            .assertIsSelected()
    }
}
