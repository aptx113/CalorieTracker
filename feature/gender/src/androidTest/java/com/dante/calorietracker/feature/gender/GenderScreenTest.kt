package com.dante.calorietracker.feature.gender


import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dante.calorietracker.core.model.Gender
import org.junit.Rule
import org.junit.Test

class GenderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val maleChipMatcher by lazy {
        hasText(
            composeTestRule.activity.resources.getString(R.string.male),
        )
    }

    private val feMaleChipMatcher by lazy {
        hasText(
            composeTestRule.activity.resources.getString(R.string.female),
        )
    }

    private val otherChipMatcher by lazy {
        hasText(
            composeTestRule.activity.resources.getString(R.string.other)
        )
    }

    @Test
    fun otherChipByDefault_isSelected() {
        composeTestRule.setContent {
            GenderScreen(
                selectedGender = Gender.Other,
                onSelectedChange = {},
                onNextClick = {},
            )
        }
        composeTestRule
            .onNode(otherChipMatcher)
            .assertIsSelected()
    }
}
