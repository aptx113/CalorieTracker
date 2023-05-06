package com.dante.calorietracker.feature.gender


import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import org.junit.Before
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

    private val viewModel = GenderViewModel(TestUserDataRepository())

    @Before
    fun setup() {
        composeTestRule.setContent {
            GenderScreen(
                selectedGender = viewModel.selectedGender,
                onSelectedChange = { viewModel.onGenderClick(it) },
                onNextClick = {},
            )
        }
    }

    @Test
    fun otherChipByDefault_isSelected() {
        composeTestRule
            .onNode(otherChipMatcher)
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickMale_maleIsSelected() {
        composeTestRule
            .onNode(maleChipMatcher)
            .performClick()
            .assertIsSelected()
    }

    @Test
    fun chip_whenClickFemale_femaleIsSelected() {
        composeTestRule
            .onNode(feMaleChipMatcher)
            .performClick()
            .assertIsSelected()
    }
}
