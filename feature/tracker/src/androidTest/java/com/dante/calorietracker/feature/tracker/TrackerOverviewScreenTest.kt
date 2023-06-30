package com.dante.calorietracker.feature.tracker

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import com.dante.calorietracker.core.domain.CalculateMealNutrientsUseCase
import com.dante.calorietracker.core.domain.DeleteTrackedFoodUseCase
import com.dante.calorietracker.core.domain.GetFoodsForDateUseCase
import com.dante.calorietracker.core.testing.data.repository.TestTrackerRepository
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.ui.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrackerOverviewScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var breakfast: String
    private lateinit var lunch: String
    private lateinit var dinner: String
    private lateinit var snack: String

    private lateinit var addBreakfast: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            breakfast = getString(R.string.breakfast).lowercase()
            lunch = getString(R.string.lunch).lowercase()
            dinner = getString(R.string.dinner).lowercase()
            snack = getString(R.string.snack).lowercase()

            addBreakfast = getString(R.string.add_meal, breakfast)
        }
    }

    @Test
    fun expandableMeal_isNotExpand_whenClick_showAddButton() {
        val viewModel = provideViewModel()
        composeTestRule.setContent {
            TrackerOverviewScreen(onToggleClick = { viewModel.onToggleMealClick(it) })
        }

        composeTestRule.onNodeWithText(breakfast)
            .assertExists()
        composeTestRule.onNodeWithText(breakfast)
            .onParent()
            .onParent()
            .performClick()

        composeTestRule.onNodeWithText(addBreakfast)
            .assertExists()
    }


    private fun provideViewModel() = TrackerOverviewViewModel(
        deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(TestTrackerRepository()),
        getFoodsForDateUseCase = GetFoodsForDateUseCase(TestTrackerRepository()),
        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(TestUserDataRepository())
    )
}
