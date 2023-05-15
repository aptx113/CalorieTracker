package com.dante.calorietracker.feature.activity

import app.cash.turbine.test
import com.dante.calorietracker.core.model.ActivityLevel
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class ActivityViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = TestUserDataRepository()
    private val viewModel = ActivityViewModel(repository)

    @Test
    fun selectedActivityLevel_defaultIsMedium() {
        assertEquals(viewModel.selectedActivity, ActivityLevel.Medium)
    }

    @Test
    fun onActivityLevelSelect_selectHigh_selectedActivityIsHigh() {
        viewModel.onActivityLevelSelect(ActivityLevel.High)
        assertEquals(viewModel.selectedActivity, ActivityLevel.High)
    }

    @Test
    fun onNextClick_selectHigh_userInfoReturnsActivityHigh() = runTest {
        viewModel.onActivityLevelSelect(ActivityLevel.High)
        viewModel.onNextClick { }
        repository.userInfo.test {
            assertEquals(emptyUserInfo.copy(activityLevel = ActivityLevel.High), awaitItem())
        }
    }
}
