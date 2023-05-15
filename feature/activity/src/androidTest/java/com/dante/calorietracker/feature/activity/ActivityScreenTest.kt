package com.dante.calorietracker.feature.activity

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

class ActivityScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var activityHighChipMatcher: SemanticsMatcher

    private lateinit var activityMediumChipMatcher: SemanticsMatcher

    private lateinit var activityLowChipMatcher: SemanticsMatcher

    private val viewModel = ActivityViewModel(TestUserDataRepository())

    @Before
    fun setup() {
        composeTestRule.activity.resources.apply {
            activityHighChipMatcher = hasText(getString(R.string.high))
            activityMediumChipMatcher = hasText(getString(R.string.medium))
            activityLowChipMatcher = hasText(getString(R.string.low))
        }
        composeTestRule.setContent {
            ActivityScreen(
                selectedActivity = viewModel.selectedActivity,
                onSelectedChange = { viewModel.onActivityLevelSelect(it) },
                onNextClick = {},
            )
        }
    }

    @Test
    fun activityMediumChipByDefault_isSelected() {
        composeTestRule
            .onNode(activityMediumChipMatcher)
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickHigh_highIsSelected() {
        composeTestRule
            .onNode(activityHighChipMatcher)
            .performClick()
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickLow_LowIsSelected() {
        composeTestRule
            .onNode(activityLowChipMatcher)
            .performClick()
            .assertIsSelected()
    }
}
